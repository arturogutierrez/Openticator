package com.arturogutierrez.openticator.domain.account.interactor

import com.arturogutierrez.openticator.domain.account.interactor.CopyToClipboardInteractor.Params
import com.arturogutierrez.openticator.domain.account.model.AccountPasscode
import com.arturogutierrez.openticator.executor.PostExecutionThread
import com.arturogutierrez.openticator.executor.ThreadExecutor
import com.arturogutierrez.openticator.interactor.CompletableUseCase
import com.arturogutierrez.openticator.storage.clipboard.ClipboardRepository
import io.reactivex.Completable

class CopyToClipboardInteractor(private val clipboardRepository: ClipboardRepository,
                                threadExecutor: ThreadExecutor,
                                postExecutionThread: PostExecutionThread)
  : CompletableUseCase<Params>(threadExecutor, postExecutionThread) {

  public override fun buildObservable(params: Params): Completable {
    val accountPasscode = params.accountPasscode
    return Completable.fromCallable {
      clipboardRepository.copy(accountPasscode.account.name, accountPasscode.passcode.code)
    }
  }

  data class Params(val accountPasscode: AccountPasscode)
}
