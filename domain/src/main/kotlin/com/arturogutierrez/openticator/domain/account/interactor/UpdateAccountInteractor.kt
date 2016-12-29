package com.arturogutierrez.openticator.domain.account.interactor

import com.arturogutierrez.openticator.domain.account.interactor.UpdateAccountInteractor.Params
import com.arturogutierrez.openticator.domain.account.model.Account
import com.arturogutierrez.openticator.domain.account.repository.AccountRepository
import com.arturogutierrez.openticator.executor.PostExecutionThread
import com.arturogutierrez.openticator.executor.ThreadExecutor
import com.arturogutierrez.openticator.interactor.Interactor
import rx.Observable

class UpdateAccountInteractor(private val accountRepository: AccountRepository,
                              threadExecutor: ThreadExecutor,
                              postExecutionThread: PostExecutionThread) : Interactor<Params, Account>(threadExecutor, postExecutionThread) {

  override fun createObservable(params: Params): Observable<Account> {
    return accountRepository.update(params.account)
  }

  data class Params(val account: Account)
}
