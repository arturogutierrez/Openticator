package com.arturogutierrez.openticator.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.arturogutierrez.openticator.application.OpenticatorApplication
import com.arturogutierrez.openticator.domain.bootstrap.InitialScreenSelector
import com.arturogutierrez.openticator.domain.bootstrap.StorageInitializator
import com.arturogutierrez.openticator.domain.navigator.Navigator
import javax.inject.Inject

class ProxyActivity : AppCompatActivity() {

  @Inject
  internal lateinit var navigator: Navigator
  @Inject
  internal lateinit var screenSelector: InitialScreenSelector

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    initializeInjector()
  }

  override fun onResume() {
    super.onResume()

    if (screenSelector.shouldShowWizard()) {
      navigator.goToInitialWizard(this)
    } else {
      navigator.goToAccountList(this)
    }

    overridePendingTransition(0, 0)
    finish()
  }

  private fun initializeInjector() {
    (application as OpenticatorApplication).applicationComponent.inject(this)
  }
}
