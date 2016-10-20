package com.arturogutierrez.openticator.domain.account.interactor

import com.arturogutierrez.openticator.domain.account.AccountFactory
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
                           val accountFactory: AccountFactory,
                           val categoryRepository: CategoryRepository,
                           val categorySelector: CategorySelector,
                           val categoryFactory: CategoryFactory,
                           val threadExecutor: ThreadExecutor,
                           val postExecutionThread: PostExecutionThread) : Interactor<Account>(threadExecutor, postExecutionThread) {

  private lateinit var newAccount: Account

  fun configure(accountName: String, accountSecret: String) {
    newAccount = accountFactory.createAccount(accountName, accountSecret)
  }

  override fun createObservable(): Observable<Account> {
    return categorySelector.selectedCategory.flatMap { category ->
      val emptyCategory = categoryFactory.createEmptyCategory()
      if (category == emptyCategory) {
        return@flatMap accountRepository.add(newAccount)
      }

      return@flatMap accountRepository.add(newAccount).flatMap { createdAccount ->
        categoryRepository.addAccount(category, createdAccount).flatMap { Observable.just(createdAccount) }
      }
    }
  }
}
