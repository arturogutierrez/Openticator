package com.arturogutierrez.openticator.domain.account.repository

import com.arturogutierrez.openticator.domain.account.model.Account
import com.arturogutierrez.openticator.domain.category.model.Category
import rx.Observable
import javax.inject.Inject

class AccountRepository @Inject constructor(val accountDataStore: AccountDataStore) {

  fun add(account: Account): Observable<Account> {
    return accountDataStore.add(account)
  }

  fun update(account: Account): Observable<Account> {
    return accountDataStore.update(account)
  }

  fun remove(account: Account): Observable<Unit> {
    return accountDataStore.remove(account)
  }

  fun getAccounts(category: Category): Observable<List<Account>> {
    return accountDataStore.getAccounts(category)
  }

  val allAccounts: Observable<List<Account>>
    get() = accountDataStore.allAccounts
}
