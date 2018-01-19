package com.arturogutierrez.openticator.domain.category.interactor

import com.arturogutierrez.openticator.domain.account.model.Account
import com.arturogutierrez.openticator.domain.category.interactor.AddAccountToCategoryInteractor.Params
import com.arturogutierrez.openticator.domain.category.model.Category
import com.arturogutierrez.openticator.domain.category.repository.CategoryRepository
import com.arturogutierrez.openticator.executor.PostExecutionThread
import com.arturogutierrez.openticator.executor.ThreadExecutor
import com.arturogutierrez.openticator.interactor.SingleUseCase
import io.reactivex.Single

class AddAccountToCategoryInteractor(private val categoryRepository: CategoryRepository,
                                     threadExecutor: ThreadExecutor,
                                     postExecutionThread: PostExecutionThread)
  : SingleUseCase<Category, Params>(threadExecutor, postExecutionThread) {

  override fun buildObservable(params: Params): Single<Category> {
    val category = params.category
    val accountToAddToCategory = params.account
    return categoryRepository.addAccount(category, accountToAddToCategory)
  }

  data class Params(val category: Category, val account: Account)
}
