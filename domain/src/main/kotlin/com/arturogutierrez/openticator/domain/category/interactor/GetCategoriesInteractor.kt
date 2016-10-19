package com.arturogutierrez.openticator.domain.category.interactor

import com.arturogutierrez.openticator.domain.category.model.Category
import com.arturogutierrez.openticator.domain.category.repository.CategoryRepository
import com.arturogutierrez.openticator.executor.PostExecutionThread
import com.arturogutierrez.openticator.executor.ThreadExecutor
import com.arturogutierrez.openticator.interactor.Interactor
import rx.Observable

class GetCategoriesInteractor(val categoryRepository: CategoryRepository,
                              val threadExecutor: ThreadExecutor,
                              val postExecutionThread: PostExecutionThread) : Interactor<List<Category>>(threadExecutor, postExecutionThread) {

    override fun createObservable(): Observable<List<Category>> {
        return categoryRepository.categories
    }
}
