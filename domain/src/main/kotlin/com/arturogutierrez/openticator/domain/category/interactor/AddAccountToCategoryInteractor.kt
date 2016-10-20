package com.arturogutierrez.openticator.domain.category.interactor

import com.arturogutierrez.openticator.domain.account.model.Account
import com.arturogutierrez.openticator.domain.category.model.Category
import com.arturogutierrez.openticator.domain.category.repository.CategoryRepository
import com.arturogutierrez.openticator.executor.PostExecutionThread
import com.arturogutierrez.openticator.executor.ThreadExecutor
import com.arturogutierrez.openticator.interactor.Interactor
import rx.Observable

class AddAccountToCategoryInteractor(val categoryRepository: CategoryRepository,
                                     val threadExecutor: ThreadExecutor,
                                     val postExecutionThread: PostExecutionThread) : Interactor<Category>(threadExecutor, postExecutionThread) {

  private lateinit var category: Category
  private lateinit var accountToAddToCategory: Account

  fun configure(category: Category, account: Account) {
    this.category = category
    this.accountToAddToCategory = account
  }

  override fun createObservable(): Observable<Category> {
    return categoryRepository.addAccount(category, accountToAddToCategory)
  }
}
