package com.arturogutierrez.openticator.domain.account.interactor

import com.arturogutierrez.openticator.domain.account.interactor.GetAccountsInteractor.EmptyParams
import com.arturogutierrez.openticator.domain.account.model.Account
import com.arturogutierrez.openticator.domain.account.repository.AccountRepository
import com.arturogutierrez.openticator.executor.PostExecutionThread
import com.arturogutierrez.openticator.executor.ThreadExecutor
import com.arturogutierrez.openticator.interactor.FlowableUseCase
import io.reactivex.Flowable

class GetAccountsInteractor(private val accountRepository: AccountRepository,
                            threadExecutor: ThreadExecutor,
                            postExecutionThread: PostExecutionThread)
  : FlowableUseCase<List<Account>, EmptyParams>(threadExecutor, postExecutionThread) {

  override fun buildObservable(params: EmptyParams): Flowable<List<Account>> {
    return accountRepository.allAccounts
  }

  object EmptyParams
}
