package com.arturogutierrez.openticator.domain.category.interactor

import com.arturogutierrez.openticator.domain.account.model.Account
import com.arturogutierrez.openticator.domain.category.interactor.AddAccountToCategoryInteractor.Params
import com.arturogutierrez.openticator.domain.category.model.Category
import com.arturogutierrez.openticator.domain.category.repository.CategoryRepository
import com.arturogutierrez.openticator.executor.PostExecutionThread
import com.arturogutierrez.openticator.executor.ThreadExecutor
import com.arturogutierrez.openticator.interactor.Interactor
import rx.Observable

class AddAccountToCategoryInteractor(val categoryRepository: CategoryRepository,
                                     val threadExecutor: ThreadExecutor,
                                     val postExecutionThread: PostExecutionThread) : Interactor<Params, Category>(threadExecutor, postExecutionThread) {

  override fun createObservable(params: Params): Observable<Category> {
    val category = params.category
    val accountToAddToCategory = params.account
    return categoryRepository.addAccount(category, accountToAddToCategory)
  }

  data class Params(val category: Category, val account: Account)
}
