package com.arturogutierrez.openticator.domain.category.interactor

import com.arturogutierrez.openticator.domain.account.model.Account
import com.arturogutierrez.openticator.domain.category.CategoryFactory
import com.arturogutierrez.openticator.domain.category.interactor.AddCategoryInteractor.Params
import com.arturogutierrez.openticator.domain.category.model.Category
import com.arturogutierrez.openticator.domain.category.repository.CategoryRepository
import com.arturogutierrez.openticator.executor.PostExecutionThread
import com.arturogutierrez.openticator.executor.ThreadExecutor
import com.arturogutierrez.openticator.interactor.Interactor
import rx.Observable

class AddCategoryInteractor(private val categoryRepository: CategoryRepository,
                            private val categoryFactory: CategoryFactory,
                            threadExecutor: ThreadExecutor,
                            postExecutionThread: PostExecutionThread) : Interactor<Params, Category>(threadExecutor, postExecutionThread) {

  override fun createObservable(params: Params): Observable<Category> {
    val newCategory = categoryFactory.createCategory(params.categoryName)
    val accountToAddToCategory = params.account
    return categoryRepository.add(newCategory)
        .flatMap { category -> categoryRepository.addAccount(category, accountToAddToCategory) }
  }

  data class Params(val categoryName: String, val account: Account)
}
