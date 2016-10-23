package com.arturogutierrez.openticator.domain.account.interactor

import com.arturogutierrez.openticator.domain.account.model.AccountPasscode
import com.arturogutierrez.openticator.executor.PostExecutionThread
import com.arturogutierrez.openticator.executor.ThreadExecutor
import com.arturogutierrez.openticator.interactor.Interactor
import com.arturogutierrez.openticator.storage.clipboard.ClipboardRepository
import rx.Observable

class CopyToClipboardInteractor(val clipboardRepository: ClipboardRepository,
                                threadExecutor: ThreadExecutor,
                                postExecutionThread: PostExecutionThread) : Interactor<Unit>(threadExecutor, postExecutionThread) {

  private lateinit var accountPasscode: AccountPasscode

  fun configure(accountPasscode: AccountPasscode) {
    this.accountPasscode = accountPasscode
  }

  override fun createObservable(): Observable<Unit> {
    return Observable.fromCallable {
      clipboardRepository.copy(accountPasscode.account.name, accountPasscode.passcode.code)
    }
  }
}
