package com.arturogutierrez.openticator.domain.account.interactor

import com.arturogutierrez.openticator.domain.account.interactor.CopyToClipboardInteractor.Params
import com.arturogutierrez.openticator.domain.account.model.AccountPasscode
import com.arturogutierrez.openticator.executor.PostExecutionThread
import com.arturogutierrez.openticator.executor.ThreadExecutor
import com.arturogutierrez.openticator.interactor.Interactor
import com.arturogutierrez.openticator.storage.clipboard.ClipboardRepository
import rx.Observable

class CopyToClipboardInteractor(private val clipboardRepository: ClipboardRepository,
                                threadExecutor: ThreadExecutor,
                                postExecutionThread: PostExecutionThread) : Interactor<Params, Unit>(threadExecutor, postExecutionThread) {

  override fun createObservable(params: Params): Observable<Unit> {
    val accountPasscode = params.accountPasscode
    return Observable.fromCallable {
      clipboardRepository.copy(accountPasscode.account.name, accountPasscode.passcode.code)
    }
  }

  data class Params(val accountPasscode: AccountPasscode)
}
