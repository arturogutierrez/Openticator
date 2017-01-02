package com.arturogutierrez.openticator.domain.category.interactor

import com.arturogutierrez.openticator.domain.category.interactor.GetCategoriesInteractor.EmptyParams
import com.arturogutierrez.openticator.domain.category.model.Category
import com.arturogutierrez.openticator.domain.category.repository.CategoryRepository
import com.arturogutierrez.openticator.executor.PostExecutionThread
import com.arturogutierrez.openticator.executor.ThreadExecutor
import com.arturogutierrez.openticator.interactor.Interactor
import rx.Observable

class GetCategoriesInteractor(private val categoryRepository: CategoryRepository,
                              threadExecutor: ThreadExecutor,
                              postExecutionThread: PostExecutionThread) : Interactor<EmptyParams, List<Category>>(threadExecutor, postExecutionThread) {

  override fun createObservable(params: EmptyParams): Observable<List<Category>> {
    return categoryRepository.categories
  }

  object EmptyParams
}
