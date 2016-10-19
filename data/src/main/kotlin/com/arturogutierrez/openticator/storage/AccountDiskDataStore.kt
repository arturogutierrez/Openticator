package com.arturogutierrez.openticator.storage

import com.arturogutierrez.openticator.domain.account.model.Account
import com.arturogutierrez.openticator.domain.account.repository.AccountDataStore
import com.arturogutierrez.openticator.domain.category.model.Category
import com.arturogutierrez.openticator.storage.realm.mapper.AccountRealmMapper
import com.arturogutierrez.openticator.storage.realm.model.AccountRealm
import io.realm.Realm
import io.realm.Sort
import rx.Observable
import rx.subjects.PublishSubject
import rx.subjects.Subject
import javax.inject.Inject

class AccountDiskDataStore @Inject constructor(private val accountRealmMapper: AccountRealmMapper) : AccountDataStore {
    private val changesPublishSubject: Subject<Void, Void>

    init {
        this.changesPublishSubject = PublishSubject.create<Void>().toSerialized()
    }

    override fun add(account: Account): Observable<Account> {
        val accountObservable = allAccounts.flatMap { accounts ->
            val numberOfAccounts = accounts.size

            Observable.fromCallable {
                val accountRealm = accountRealmMapper.transform(account)
                accountRealm.order = numberOfAccounts

                val defaultRealm = Realm.getDefaultInstance()
                defaultRealm.executeTransaction { realm -> realm.copyToRealm(accountRealm) }

                account
            }
        }

        return accountObservable.doOnNext { accountAdded -> notifyAccountChanges() }
    }

    override fun update(account: Account): Observable<Account> {
        val updateAccountObservable = Observable.fromCallable {
            val defaultRealm = Realm.getDefaultInstance()
            defaultRealm.executeTransaction { realm ->
                val accountRealm = getAccountRealmAsBlocking(realm, account.accountId) ?: return@executeTransaction
                accountRealmMapper.copyToAccountRealm(accountRealm, account)
            }

            account
        }

        return updateAccountObservable.doOnNext { aVoid -> notifyAccountChanges() }
    }

    override fun remove(account: Account): Observable<Void> {
        val removeAccountObservable = Observable.fromCallable<Void> {
            val defaultRealm = Realm.getDefaultInstance()
            defaultRealm.executeTransaction { realm ->
                val accountRealm = getAccountRealmAsBlocking(realm, account.accountId) ?: return@executeTransaction
                accountRealm.removeFromRealm()
            }

            null
        }

        return removeAccountObservable.doOnNext { aVoid -> notifyAccountChanges() }
    }

    override fun getAccounts(category: Category): Observable<List<Account>> {
        return changesPublishSubject.map { getAccountsForCategoryAsBlocking(category) }
                .startWith(Observable.fromCallable { getAccountsForCategoryAsBlocking(category) })
    }

    override val allAccounts: Observable<List<Account>>
        get() = changesPublishSubject.map { accountsAsBlocking }.startWith(Observable.just(accountsAsBlocking))

    private val accountsAsBlocking: List<Account>
        get() {
            val realm = Realm.getDefaultInstance()
            realm.refresh()
            val realmResults = realm.where(AccountRealm::class.java).findAllSorted("order", Sort.ASCENDING)
            return accountRealmMapper.reverseTransform(realmResults)
        }

    private fun getAccountsForCategoryAsBlocking(category: Category): List<Account> {
        val realm = Realm.getDefaultInstance()
        realm.refresh()

        val realmResults = realm.where(AccountRealm::class.java).equalTo("category.categoryId", category.categoryId).findAllSorted("order", Sort.ASCENDING)
        return accountRealmMapper.reverseTransform(realmResults)
    }

    private fun getAccountRealmAsBlocking(realm: Realm, accountId: String): AccountRealm? {
        return realm.where(AccountRealm::class.java).equalTo("accountId", accountId).findFirst()
    }

    private fun notifyAccountChanges() {
        changesPublishSubject.onNext(null)
    }
}
