package com.arturogutierrez.openticator.domain.cloud

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import rx.Observable
import rx.subscriptions.Subscriptions

fun observeFirebaseAuth(firebaseAuth: FirebaseAuth): Observable<FirebaseAuth> = Observable.create { subscriber ->
  val listener = FirebaseAuth.AuthStateListener {
    subscriber.onNext(it)
  }

  subscriber.add(Subscriptions.create { firebaseAuth.removeAuthStateListener(listener) })
}

fun firebaseSignInWithCredential(firebaseAuth: FirebaseAuth, credential: AuthCredential): Observable<AuthResult> = Observable.create { subscriber ->
  val task = firebaseAuth.signInWithCredential(credential)
  task.addOnSuccessListener {
    if (!subscriber.isUnsubscribed) {
      subscriber.onNext(it)
    }
  }
  task.addOnFailureListener {
    if (!subscriber.isUnsubscribed) {
      subscriber.onError(it)
    }
  }
  task.addOnCompleteListener {
    if (!subscriber.isUnsubscribed) {
      subscriber.onCompleted()
    }
  }
}
