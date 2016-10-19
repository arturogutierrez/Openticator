package com.arturogutierrez.openticator.domain.category.repository

import com.arturogutierrez.openticator.domain.account.model.Account
import com.arturogutierrez.openticator.domain.category.model.Category
import com.arturogutierrez.openticator.storage.CategoryDiskDataStore
import javax.inject.Inject
import rx.Observable

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
