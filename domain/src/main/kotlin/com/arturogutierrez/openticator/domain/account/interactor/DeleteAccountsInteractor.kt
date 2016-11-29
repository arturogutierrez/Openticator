package com.arturogutierrez.openticator.domain.account.interactor

import com.arturogutierrez.openticator.domain.account.model.Account
import com.arturogutierrez.openticator.domain.account.repository.AccountRepository
import com.arturogutierrez.openticator.executor.PostExecutionThread
import com.arturogutierrez.openticator.executor.ThreadExecutor
import com.arturogutierrez.openticator.interactor.Interactor
import rx.Observable

class DeleteAccountsInteractor(val accountRepository: AccountRepository,
                               threadExecutor: ThreadExecutor,
                               postExecutionThread: PostExecutionThread) : Interactor<Unit>(threadExecutor, postExecutionThread) {
  private lateinit var accounts: List<Account>

  fun configure(accounts: List<Account>) {
    this.accounts = accounts
  }

  override fun createObservable(): Observable<Unit> {
    if (accounts.isEmpty()) {
      throw IllegalStateException("You can't delete no one accounts")
    }

    return Observable.merge(accounts.map { accountRepository.remove(it) })
  }
}
