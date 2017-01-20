package com.arturogutierrez.openticator.domain.cloud

import io.reactivex.Flowable

interface SessionManager {

  val currentSession : Session

  fun onSession() : Flowable<Session>

}
