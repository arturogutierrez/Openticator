package com.arturogutierrez.openticator.domain.account.interactor

import com.arturogutierrez.openticator.domain.account.interactor.UpdateAccountInteractor.Params
import com.arturogutierrez.openticator.domain.account.model.Account
import com.arturogutierrez.openticator.domain.account.repository.AccountRepository
import com.arturogutierrez.openticator.executor.PostExecutionThread
import com.arturogutierrez.openticator.executor.ThreadExecutor
import com.arturogutierrez.openticator.interactor.CompletableUseCase
import io.reactivex.Completable

class UpdateAccountInteractor(private val accountRepository: AccountRepository,
                              threadExecutor: ThreadExecutor,
                              postExecutionThread: PostExecutionThread) : CompletableUseCase<Params>(threadExecutor, postExecutionThread) {

  override fun buildObservable(params: Params): Completable {
    return accountRepository.update(params.account).toCompletable()
  }

  data class Params(val account: Account)
}
