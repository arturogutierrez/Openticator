package com.arturogutierrez.openticator.domain.account.repository

import com.arturogutierrez.openticator.domain.account.model.Account
import com.arturogutierrez.openticator.domain.category.model.Category
import rx.Observable

interface AccountRepository {

  fun add(account: Account): Observable<Account>

  fun update(account: Account): Observable<Account>

  fun remove(account: Account): Observable<Unit>

  fun getAccounts(category: Category): Observable<List<Account>>

  val allAccounts: Observable<List<Account>>
}
