package com.arturogutierrez.openticator.domain.account.repository

import com.arturogutierrez.openticator.domain.account.model.Account
import com.arturogutierrez.openticator.domain.category.model.Category
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class AccountRepository @Inject constructor(private val accountDataStore: AccountDataStore) {

  fun add(account: Account): Single<Account> {
    return accountDataStore.add(account)
  }

  fun update(account: Account): Single<Account> {
    return accountDataStore.update(account)
  }

  fun remove(account: Account): Completable {
    return accountDataStore.remove(account)
  }

  fun getAccounts(category: Category): Flowable<List<Account>> {
    return accountDataStore.getAccounts(category)
  }

  val allAccounts: Flowable<List<Account>>
    get() = accountDataStore.allAccounts
}
