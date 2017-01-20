package com.arturogutierrez.openticator.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.arturogutierrez.openticator.application.OpenticatorApplication
import com.arturogutierrez.openticator.domain.bootstrap.InitialScreenSelector
import com.arturogutierrez.openticator.domain.bootstrap.InitialScreenSelector.InitialScreen.*
import com.arturogutierrez.openticator.domain.navigator.Navigator
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

  @Inject
  lateinit var navigator: Navigator
  @Inject
  lateinit var screenSelector: InitialScreenSelector

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    initializeInjector()
  }

  override fun onResume() {
    super.onResume()

    when (screenSelector.initialScreen()) {
      LOGIN -> navigator.goToWelcomeScreen(this)
      MAIN_SCREEN -> navigator.goToAccountList(this)
    }

    finish()
  }

  private fun initializeInjector() {
    (application as OpenticatorApplication).applicationComponent.inject(this)
  }
}
