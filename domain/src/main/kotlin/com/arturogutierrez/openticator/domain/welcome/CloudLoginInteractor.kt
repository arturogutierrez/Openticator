package com.arturogutierrez.openticator.domain.welcome

import com.arturogutierrez.openticator.domain.cloud.Session
import com.arturogutierrez.openticator.domain.welcome.CloudLoginInteractor.Params
import com.arturogutierrez.openticator.executor.PostExecutionThread
import com.arturogutierrez.openticator.executor.ThreadExecutor
import com.arturogutierrez.openticator.interactor.SingleUseCase
import io.reactivex.Single

class CloudLoginInteractor(threadExecutor: ThreadExecutor,
                           postExecutionThread: PostExecutionThread) : SingleUseCase<Session, Params>(threadExecutor, postExecutionThread) {

  override fun buildObservable(params: Params): Single<Session> {
    return params.googleLoginObservable.map { if (it) Session.Active else Session.Invalid }
  }

  data class Params(val googleLoginObservable: Single<Boolean>)
}
