package com.arturogutierrez.openticator.domain.welcome

import com.arturogutierrez.openticator.domain.cloud.Session
import com.arturogutierrez.openticator.domain.welcome.CloudLoginInteractor.Params
import com.arturogutierrez.openticator.executor.PostExecutionThread
import com.arturogutierrez.openticator.executor.ThreadExecutor
import com.arturogutierrez.openticator.interactor.Interactor
import rx.Observable

class CloudLoginInteractor(threadExecutor: ThreadExecutor,
                           postExecutionThread: PostExecutionThread) : Interactor<Params, Session>(threadExecutor, postExecutionThread) {

  override fun createObservable(params: Params): Observable<Session> {
    return params.googleLoginObservable.map { if (it) Session.Active else Session.Invalid }
  }

  data class Params(val googleLoginObservable: Observable<Boolean>)
}
