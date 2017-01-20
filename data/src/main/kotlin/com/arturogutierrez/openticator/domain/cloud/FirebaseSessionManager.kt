package com.arturogutierrez.openticator.domain.cloud

import com.google.firebase.auth.FirebaseAuth
import rx.Observable
import javax.inject.Inject

class FirebaseSessionManager @Inject constructor() : SessionManager {

  private var currentSession = Session.Invalid
  private val firebaseAuth = FirebaseAuth.getInstance()

  override fun getCurrentSession() = currentSession

  override fun onSession(): Observable<Session> = observeFirebaseAuth(firebaseAuth)
      .map {
        if (it.currentUser != null) {
          Session.Active
        } else {
          Session.Invalid
        }
      }.doOnNext { currentSession = it }
}
