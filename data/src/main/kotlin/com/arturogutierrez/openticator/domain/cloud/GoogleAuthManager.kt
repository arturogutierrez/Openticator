package com.arturogutierrez.openticator.domain.cloud

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import io.reactivex.Single

class GoogleAuthManager(context: Context, requestIdToken: String) {

  companion object {
    val RC_GOOGLE_LOGIN = 0x1234
  }

  private val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
      .requestIdToken(requestIdToken)
      .requestEmail()
      .build()
  private val googleApiClient = GoogleApiClient.Builder(context)
      .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
      .build()
  private val firebaseAuth = FirebaseAuth.getInstance()

  fun signIn(activity: Activity) {
    val signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient)
    activity.startActivityForResult(signInIntent, RC_GOOGLE_LOGIN)
  }

  fun loginWithGoogle(requestCode: Int, data: Intent?): Single<Boolean> {
    if (requestCode != RC_GOOGLE_LOGIN) {
      return Single.just(false)
    }

    val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
    if (result.isSuccess) {
      val credential = GoogleAuthProvider.getCredential(result.signInAccount!!.idToken, null)
      return firebaseSignInWithCredential(firebaseAuth, credential).map { it.user != null }
    }

    return Single.just(false)
  }
}
