package com.arturogutierrez.openticator.domain.cloud

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.BackpressureStrategy.BUFFER
import io.reactivex.Flowable
import io.reactivex.Single

fun observeFirebaseAuth(firebaseAuth: FirebaseAuth): Flowable<FirebaseAuth> =
    Flowable.create({ subscriber ->
      val listener = FirebaseAuth.AuthStateListener {
        subscriber.onNext(it)
      }

      firebaseAuth.addAuthStateListener(listener)
    }, BUFFER)

fun firebaseSignInWithCredential(firebaseAuth: FirebaseAuth, credential: AuthCredential): Single<AuthResult> =
    Single.create { subscriber ->
      val task = firebaseAuth.signInWithCredential(credential)
      task.addOnSuccessListener {
        if (!subscriber.isDisposed) {
          subscriber.onSuccess(it)
        }
      }
      task.addOnFailureListener {
        if (!subscriber.isDisposed) {
          subscriber.onError(it)
        }
      }
    }
