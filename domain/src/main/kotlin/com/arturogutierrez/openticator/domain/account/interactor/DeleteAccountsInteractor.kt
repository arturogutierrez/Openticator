package com.arturogutierrez.openticator.domain.account.interactor

import com.arturogutierrez.openticator.domain.account.model.Account
import com.arturogutierrez.openticator.domain.account.repository.AccountRepository
import com.arturogutierrez.openticator.executor.PostExecutionThread
import com.arturogutierrez.openticator.executor.ThreadExecutor
import com.arturogutierrez.openticator.interactor.Interactor
import rx.Observable

class DeleteAccountsInteractor(private val accountRepository: AccountRepository,
                               threadExecutor: ThreadExecutor,
                               postExecutionThread: PostExecutionThread) : Interactor<DeleteAccountsInteractor.Params, Unit>(threadExecutor, postExecutionThread) {

  override fun createObservable(params: Params): Observable<Unit> {
    val accounts = params.accounts
    if (accounts.isEmpty()) {
      throw IllegalStateException("You can't delete zero accounts")
    }
    return Observable.merge(accounts.map { accountRepository.remove(it) })
  }

  data class Params(val accounts: List<Account>)
}
