package com.arturogutierrez.openticator.domain.category.repository

import com.arturogutierrez.openticator.domain.account.model.Account
import com.arturogutierrez.openticator.domain.category.model.Category
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class CategoryRepository @Inject constructor(private val categoryDataStore: CategoryDataStore) {

  fun add(category: Category): Single<Category> {
    return categoryDataStore.add(category)
  }

  fun addAccount(category: Category, account: Account): Single<Category> {
    return categoryDataStore.addAccount(category, account)
  }

  val categories: Flowable<List<Category>>
    get() = categoryDataStore.categories
}
