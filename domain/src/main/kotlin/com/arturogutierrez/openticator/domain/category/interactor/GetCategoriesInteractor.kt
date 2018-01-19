package com.arturogutierrez.openticator.domain.category.interactor

import com.arturogutierrez.openticator.domain.category.interactor.GetCategoriesInteractor.EmptyParams
import com.arturogutierrez.openticator.domain.category.model.Category
import com.arturogutierrez.openticator.domain.category.repository.CategoryRepository
import com.arturogutierrez.openticator.executor.PostExecutionThread
import com.arturogutierrez.openticator.executor.ThreadExecutor
import com.arturogutierrez.openticator.interactor.FlowableUseCase
import io.reactivex.Flowable

class GetCategoriesInteractor(private val categoryRepository: CategoryRepository,
                              threadExecutor: ThreadExecutor,
                              postExecutionThread: PostExecutionThread)
  : FlowableUseCase<List<Category>, EmptyParams>(threadExecutor, postExecutionThread) {

  override fun buildObservable(params: EmptyParams): Flowable<List<Category>> {
    return categoryRepository.categories
  }

  object EmptyParams
}
