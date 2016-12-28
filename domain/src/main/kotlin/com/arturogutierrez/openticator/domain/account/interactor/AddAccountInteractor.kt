package com.arturogutierrez.openticator.domain.account.interactor

import com.arturogutierrez.openticator.domain.account.interactor.AddAccountInteractor.Params
import com.arturogutierrez.openticator.domain.account.model.Account
import com.arturogutierrez.openticator.domain.account.repository.AccountRepository
import com.arturogutierrez.openticator.domain.category.CategoryFactory
import com.arturogutierrez.openticator.domain.category.CategorySelector
import com.arturogutierrez.openticator.domain.category.repository.CategoryRepository
import com.arturogutierrez.openticator.executor.PostExecutionThread
import com.arturogutierrez.openticator.executor.ThreadExecutor
import com.arturogutierrez.openticator.interactor.Interactor
import rx.Observable

class AddAccountInteractor(val accountRepository: AccountRepository,
                           val categoryRepository: CategoryRepository,
                           val categorySelector: CategorySelector,
                           val categoryFactory: CategoryFactory,
                           threadExecutor: ThreadExecutor,
                           postExecutionThread: PostExecutionThread) : Interactor<Params, Account>(threadExecutor, postExecutionThread) {

  override fun createObservable(params: Params): Observable<Account> {
    val newAccount = params.account
    return categorySelector.selectedCategory.flatMap { category ->
      val emptyCategory = categoryFactory.createEmptyCategory()
      if (category == emptyCategory) {
        return@flatMap accountRepository.add(newAccount)
      }

      accountRepository.add(newAccount).flatMap { createdAccount ->
        categoryRepository.addAccount(category, createdAccount).flatMap { Observable.just(createdAccount) }
      }
    }
  }

  data class Params(val account: Account)
}
