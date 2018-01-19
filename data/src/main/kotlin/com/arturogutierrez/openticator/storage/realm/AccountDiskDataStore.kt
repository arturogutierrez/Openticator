package com.arturogutierrez.openticator.storage.realm

import com.arturogutierrez.openticator.domain.account.model.Account
import com.arturogutierrez.openticator.domain.account.repository.AccountDataStore
import com.arturogutierrez.openticator.domain.category.model.Category
import com.arturogutierrez.openticator.storage.realm.mapper.AccountRealmMapper
import com.arturogutierrez.openticator.storage.realm.model.AccountRealm
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.processors.PublishProcessor
import io.realm.Realm
import io.realm.Sort
import javax.inject.Inject

class AccountDiskDataStore @Inject constructor(private val accountRealmMapper: AccountRealmMapper) : AccountDataStore {

  private val changesPublishSubject = PublishProcessor.create<Unit>().toSerialized()

  override fun add(account: Account): Single<Account> {
    val accountObservable = allAccounts.firstOrError()
        .flatMap { accounts ->
          val numberOfAccounts = accounts.size

          Single.fromCallable {
            val accountRealm = accountRealmMapper.transform(account)
            accountRealm.order = numberOfAccounts

            Realm.getDefaultInstance().use {
              it.executeTransaction { it.copyToRealm(accountRealm) }
            }

            account
          }
        }

    return accountObservable.doOnSuccess { notifyAccountChanges() }
  }

  override fun update(account: Account): Single<Account> {
    val updateAccountObservable = Single.fromCallable {
      Realm.getDefaultInstance().use {
        it.executeTransaction {
          val accountRealm = getAccountRealmAsBlocking(it, account.accountId) ?: return@executeTransaction
          accountRealmMapper.copyToAccountRealm(accountRealm, account)
        }
      }

      account
    }

    return updateAccountObservable.doOnSuccess { notifyAccountChanges() }
  }

  override fun remove(account: Account): Completable {
    val removeAccountObservable = Completable.fromCallable {
      Realm.getDefaultInstance().use {
        it.executeTransaction {
          val accountRealm = getAccountRealmAsBlocking(it, account.accountId) ?: return@executeTransaction
          accountRealm.deleteFromRealm()
        }
      }
    }

    return removeAccountObservable.doOnComplete { notifyAccountChanges() }
  }

  override fun getAccounts(category: Category): Flowable<List<Account>> {
    return changesPublishSubject.map { getAccountsForCategoryAsBlocking(category) }
        .startWith(Flowable.fromCallable { getAccountsForCategoryAsBlocking(category) })
  }

  override val allAccounts: Flowable<List<Account>>
    get() = changesPublishSubject.map { accountsAsBlocking }.startWith(Flowable.just(accountsAsBlocking))

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

  private fun notifyAccountChanges() = changesPublishSubject.onNext(Unit)
}
