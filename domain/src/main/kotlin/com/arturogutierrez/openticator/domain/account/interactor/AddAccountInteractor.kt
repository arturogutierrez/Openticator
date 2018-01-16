package com.arturogutierrez.openticator.domain.account.interactor

import com.arturogutierrez.openticator.domain.account.interactor.AddAccountInteractor.Params
import com.arturogutierrez.openticator.domain.account.model.Account
import com.arturogutierrez.openticator.domain.account.repository.AccountRepository
import com.arturogutierrez.openticator.domain.category.CategoryFactory
import com.arturogutierrez.openticator.domain.category.CategorySelector
import com.arturogutierrez.openticator.domain.category.repository.CategoryRepository
import com.arturogutierrez.openticator.executor.PostExecutionThread
import com.arturogutierrez.openticator.executor.ThreadExecutor
import com.arturogutierrez.openticator.interactor.SingleUseCase
import io.reactivex.Single

class AddAccountInteractor(private val accountRepository: AccountRepository,
                           private val categoryRepository: CategoryRepository,
                           private val categorySelector: CategorySelector,
                           private val categoryFactory: CategoryFactory,
                           threadExecutor: ThreadExecutor,
                           postExecutionThread: PostExecutionThread) : SingleUseCase<Account, Params>(threadExecutor, postExecutionThread) {

  public override fun buildObservable(params: Params): Single<Account> {
    val newAccount = params.account

    return categorySelector.selectedCategory
        .singleOrError()
        .flatMap { category ->
          val emptyCategory = categoryFactory.createEmptyCategory()
          if (category == emptyCategory) {
            return@flatMap accountRepository.add(newAccount)
          }

          accountRepository.add(newAccount)
              .flatMap { createdAccount ->
                categoryRepository.addAccount(category, newAccount)
                    .flatMap { Single.just(createdAccount) }
              }
        }
  }

  data class Params(val account: Account)
}
