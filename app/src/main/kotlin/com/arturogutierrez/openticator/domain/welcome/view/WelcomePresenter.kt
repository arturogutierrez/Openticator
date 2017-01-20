package com.arturogutierrez.openticator.domain.welcome.view

import android.app.Activity
import android.content.Intent
import com.arturogutierrez.openticator.domain.cloud.GoogleAuthManager
import com.arturogutierrez.openticator.domain.cloud.Session
import com.arturogutierrez.openticator.domain.welcome.CloudLoginInteractor
import com.arturogutierrez.openticator.domain.welcome.CloudLoginInteractor.Params
import com.arturogutierrez.openticator.interactor.DefaultSingleObserver
import com.arturogutierrez.openticator.view.presenter.Presenter
import javax.inject.Inject

class WelcomePresenter @Inject constructor(private val googleAuthManager: GoogleAuthManager,
                                           private val loginInteractor: CloudLoginInteractor) : Presenter<WelcomeView>() {

  override fun destroy() {
    loginInteractor.dispose()
  }

  fun makeCloudLogin(activity: Activity) {
    view?.showLoading()

    googleAuthManager.signIn(activity)
  }

  fun onActivityResult(requestCode: Int, data: Intent?) {
    val loginObservable = googleAuthManager.loginWithGoogle(requestCode, data)
    val params = Params(loginObservable)

    loginInteractor.execute(params, object : DefaultSingleObserver<Session>() {
      override fun onSuccess(t: Session) {
        view?.hideLoading()

        if (t.isValid) {
          view?.navigateToNextScreen()
        }
      }

      override fun onError(e: Throwable) {
        view?.hideLoading()
        view?.showLoginError()
      }
    })
  }
}
