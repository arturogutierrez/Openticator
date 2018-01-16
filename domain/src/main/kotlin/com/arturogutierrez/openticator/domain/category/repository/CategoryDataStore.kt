package com.arturogutierrez.openticator.domain.category.repository

import com.arturogutierrez.openticator.domain.account.model.Account
import com.arturogutierrez.openticator.domain.category.model.Category
import io.reactivex.Flowable
import io.reactivex.Single

interface CategoryDataStore {

  fun add(category: Category): Single<Category>

  fun addAccount(category: Category, account: Account): Single<Category>

  val categories: Flowable<List<Category>>
}
