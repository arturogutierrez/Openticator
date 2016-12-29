package com.arturogutierrez.openticator.domain.account.interactor

import com.arturogutierrez.openticator.domain.account.interactor.GetAccountsInteractor.EmptyParams
import com.arturogutierrez.openticator.domain.account.model.Account
import com.arturogutierrez.openticator.domain.account.repository.AccountRepository
import com.arturogutierrez.openticator.executor.PostExecutionThread
import com.arturogutierrez.openticator.executor.ThreadExecutor
import com.arturogutierrez.openticator.interactor.Interactor
import rx.Observable

class GetAccountsInteractor(private val accountRepository: AccountRepository,
                            threadExecutor: ThreadExecutor,
                            postExecutionThread: PostExecutionThread) : Interactor<EmptyParams, List<Account>>(threadExecutor, postExecutionThread) {

  override fun createObservable(params: EmptyParams): Observable<List<Account>> {
    return accountRepository.allAccounts
  }

  object EmptyParams
}
