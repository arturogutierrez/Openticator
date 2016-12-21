package com.arturogutierrez.openticator.domain.account.repository

import com.arturogutierrez.openticator.domain.account.model.Account
import com.arturogutierrez.openticator.domain.category.model.Category
import rx.Observable

interface AccountCategoryDataStore {

  val allAccounts: Observable<List<Pair<Account, Category>>>
}
