package com.arturogutierrez.openticator.domain.cloud

import rx.Observable

interface SessionManager {

  fun getCurrentSession(): Session

  fun onSession() : Observable<Session>

}