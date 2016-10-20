package com.arturogutierrez.openticator.domain.account.repository

import com.arturogutierrez.openticator.domain.account.model.Account
import com.arturogutierrez.openticator.domain.category.model.Category
import rx.Observable
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(val accountDataStore: AccountDataStore) : AccountRepository {

  override fun add(account: Account): Observable<Account> {
    return accountDataStore.add(account)
  }

  override fun update(account: Account): Observable<Account> {
    return accountDataStore.update(account)
  }

  override fun remove(account: Account): Observable<Void> {
    return accountDataStore.remove(account)
  }

  override fun getAccounts(category: Category): Observable<List<Account>> {
    return accountDataStore.getAccounts(category)
  }

  override val allAccounts: Observable<List<Account>>
    get() = accountDataStore.allAccounts
}
