package com.arturogutierrez.openticator.domain.category.repository

import com.arturogutierrez.openticator.domain.account.model.Account
import com.arturogutierrez.openticator.domain.category.model.Category
import rx.Observable

interface CategoryRepository {

    fun add(category: Category): Observable<Category>

    fun addAccount(category: Category, account: Account): Observable<Category>

    val categories: Observable<List<Category>>
}
