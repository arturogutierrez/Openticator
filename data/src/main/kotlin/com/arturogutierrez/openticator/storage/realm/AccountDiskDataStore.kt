package com.arturogutierrez.openticator.storage.realm

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

  private val changesPublishSubject: Subject<Unit, Unit>

  init {
    changesPublishSubject = PublishSubject.create<Unit>().toSerialized()
  }

  override fun add(account: Account): Observable<Account> {
    val accountObservable = allAccounts.flatMap { accounts ->
      val numberOfAccounts = accounts.size

      Observable.fromCallable {
        val accountRealm = accountRealmMapper.transform(account)
        accountRealm.order = numberOfAccounts

        Realm.getDefaultInstance().use {
          it.executeTransaction { it.copyToRealm(accountRealm) }
        }

        account
      }
    }

    return accountObservable.doOnNext { notifyAccountChanges() }
  }

  override fun update(account: Account): Observable<Account> {
    val updateAccountObservable = Observable.fromCallable {
      Realm.getDefaultInstance().use {
        it.executeTransaction {
          val accountRealm = getAccountRealmAsBlocking(it, account.accountId) ?: return@executeTransaction
          accountRealmMapper.copyToAccountRealm(accountRealm, account)
        }
      }

      account
    }

    return updateAccountObservable.doOnNext { notifyAccountChanges() }
  }

  override fun remove(account: Account): Observable<Unit> {
    val removeAccountObservable = Observable.fromCallable<Unit> {
      Realm.getDefaultInstance().use {
        it.executeTransaction {
          val accountRealm = getAccountRealmAsBlocking(it, account.accountId) ?: return@executeTransaction
          accountRealm.deleteFromRealm()
        }
      }
    }

    return removeAccountObservable.doOnNext { notifyAccountChanges() }
  }

  override fun getAccounts(category: Category): Observable<List<Account>> {
    return changesPublishSubject.map { getAccountsForCategoryAsBlocking(category) }
        .startWith(Observable.fromCallable { getAccountsForCategoryAsBlocking(category) })
  }

  override val allAccounts: Observable<List<Account>>
    get() = changesPublishSubject.map { accountsAsBlocking }.startWith(Observable.just(accountsAsBlocking))

  private val accountsAsBlocking: List<Account>
    get() {
      Realm.getDefaultInstance().use {
        val realmResults = it.where(AccountRealm::class.java).findAllSorted("order", Sort.ASCENDING)
        return accountRealmMapper.reverseTransform(realmResults)
      }
    }

  private fun getAccountsForCategoryAsBlocking(category: Category): List<Account> {
    Realm.getDefaultInstance().use {
      val realmResults = it.where(AccountRealm::class.java)
          .equalTo("category.categoryId", category.categoryId)
          .findAllSorted("order", Sort.ASCENDING)
      return accountRealmMapper.reverseTransform(realmResults)
    }
  }

  private fun getAccountRealmAsBlocking(realm: Realm, accountId: String): AccountRealm? {
    return realm.where(AccountRealm::class.java).equalTo("accountId", accountId).findFirst()
  }

  private fun notifyAccountChanges() = changesPublishSubject.onNext(null)
}
