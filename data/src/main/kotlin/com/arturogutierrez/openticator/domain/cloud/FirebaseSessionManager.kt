package com.arturogutierrez.openticator.domain.cloud

import com.google.firebase.auth.FirebaseAuth
import io.reactivex.Flowable
import javax.inject.Inject

class FirebaseSessionManager @Inject constructor() : SessionManager {

  private val firebaseAuth = FirebaseAuth.getInstance()
  private var _currentSession = Session.Invalid

  override val currentSession: Session
    get() = _currentSession

  override fun onSession(): Flowable<Session> = observeFirebaseAuth(firebaseAuth)
      .map {
        if (it.currentUser != null) {
          Session.Active
        } else {
          Session.Invalid
        }
      }.doOnNext { _currentSession = it }
}
