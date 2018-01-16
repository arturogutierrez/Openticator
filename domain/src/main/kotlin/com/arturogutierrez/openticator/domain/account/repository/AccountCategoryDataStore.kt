package com.arturogutierrez.openticator.domain.account.repository

import com.arturogutierrez.openticator.domain.account.model.Account
import com.arturogutierrez.openticator.domain.category.model.Category
import io.reactivex.Single

interface AccountCategoryDataStore {

  val allAccounts: Single<List<Pair<Account, Category>>>
}
