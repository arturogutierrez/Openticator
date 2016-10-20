package com.arturogutierrez.openticator.domain.category.interactor

import com.arturogutierrez.openticator.domain.account.model.Account
import com.arturogutierrez.openticator.domain.category.CategoryFactory
import com.arturogutierrez.openticator.domain.category.model.Category
import com.arturogutierrez.openticator.domain.category.repository.CategoryRepository
import com.arturogutierrez.openticator.executor.PostExecutionThread
import com.arturogutierrez.openticator.executor.ThreadExecutor
import com.arturogutierrez.openticator.interactor.Interactor
import rx.Observable

class AddCategoryInteractor(val categoryRepository: CategoryRepository,
                            val categoryFactory: CategoryFactory,
                            val threadExecutor: ThreadExecutor,
                            val postExecutionThread: PostExecutionThread) : Interactor<Category>(threadExecutor, postExecutionThread) {

  private lateinit var newCategory: Category
  private lateinit var accountToAddToCategory: Account

  fun configure(categoryName: String, account: Account) {
    this.newCategory = categoryFactory.createCategory(categoryName)
    this.accountToAddToCategory = account
  }

  override fun createObservable(): Observable<Category> {
    return categoryRepository.add(newCategory)
        .flatMap { category -> categoryRepository.addAccount(category, accountToAddToCategory) }
  }
}
