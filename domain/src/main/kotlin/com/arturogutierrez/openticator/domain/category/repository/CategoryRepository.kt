package com.arturogutierrez.openticator.domain.category.repository

import com.arturogutierrez.openticator.domain.account.model.Account
import com.arturogutierrez.openticator.domain.category.model.Category
import rx.Observable
import javax.inject.Inject

class CategoryRepository @Inject constructor(val categoryDataStore: CategoryDataStore) {

  fun add(category: Category): Observable<Category> {
    return categoryDataStore.add(category)
  }

  fun addAccount(category: Category, account: Account): Observable<Category> {
    return categoryDataStore.addAccount(category, account)
  }

  val categories: Observable<List<Category>>
    get() = categoryDataStore.categories
}
