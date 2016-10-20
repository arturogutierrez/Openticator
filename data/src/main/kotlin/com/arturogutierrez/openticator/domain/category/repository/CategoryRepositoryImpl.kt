package com.arturogutierrez.openticator.domain.category.repository

import com.arturogutierrez.openticator.domain.account.model.Account
import com.arturogutierrez.openticator.domain.category.model.Category
import rx.Observable
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(val categoryDataStore: CategoryDataStore) : CategoryRepository {

  override fun add(category: Category): Observable<Category> {
    return categoryDataStore.add(category)
  }

  override fun addAccount(category: Category, account: Account): Observable<Category> {
    return categoryDataStore.addAccount(category, account)
  }

  override val categories: Observable<List<Category>>
    get() = categoryDataStore.categories
}
