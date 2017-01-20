package com.arturogutierrez.openticator.domain.welcome.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.view.ViewCompat
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.arturogutierrez.openticator.R
import com.arturogutierrez.openticator.di.HasComponent
import com.arturogutierrez.openticator.domain.welcome.di.WelcomeComponent
import com.arturogutierrez.openticator.domain.welcome.di.WelcomeModule
import com.arturogutierrez.openticator.domain.welcome.view.WelcomePresenter
import com.arturogutierrez.openticator.domain.welcome.view.WelcomeView
import com.arturogutierrez.openticator.helpers.find
import com.arturogutierrez.openticator.view.activity.BaseActivity
import com.arturogutierrez.openticator.view.activity.makeSnackbar
import com.arturogutierrez.openticator.view.widget.gone
import com.arturogutierrez.openticator.view.widget.visible
import javax.inject.Inject

class WelcomeActivity : BaseActivity(R.layout.activity_welcome), HasComponent<WelcomeComponent>, WelcomeView {

  private companion object Animation {

    val STARTUP_DELAY = 400L
    val ITEM_DELAY = 200L
    val LOGO_DURATION = 1000L
    val TEXT_DURATION = 500L
    val BUTTON_DURATION = 1000L
  }

  private val container by lazy { find<View>(R.id.welcome_container) }

  private val logoImageView by lazy { find<ImageView>(R.id.logo_iv) }

  private val logoPlaceholder by lazy { find<View>(R.id.logo_placeholder) }
  private val title by lazy { find<TextView>(R.id.title_tv) }
  private val bodyFirst by lazy { find<TextView>(R.id.title_first) }
  private val bodySecond by lazy { find<TextView>(R.id.title_second) }
  private val loadingView by lazy { find<ProgressBar>(R.id.pb_loading) }
  private val startButton by lazy { find<Button>(R.id.start_btn) }
  private val whatIsButton by lazy { find<TextView>(R.id.what_is) }
  private var animationStarted = false

  @Inject
  lateinit var presenter: WelcomePresenter

  override val component: WelcomeComponent by lazy {
    DaggerWelcomeComponent.builder()
        .applicationComponent(applicationComponent)
        .activityModule(activityModule)
        .welcomeModule(WelcomeModule())
        .build()
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    initializeInjector()

    startButton.setOnClickListener { presenter.makeCloudLogin(this) }
    whatIsButton.setOnClickListener { navigator.openWhatIsVideo(this) }
  }

  override fun onResume() {
    super.onResume()
    presenter.attachView(this)
    presenter.resume()
  }

  override fun onPause() {
    presenter.detachView()
    presenter.pause()
    super.onPause()
  }

  override fun onStop() {
    presenter.destroy()
    super.onStop()
  }

  override fun onWindowFocusChanged(hasFocus: Boolean) {
    super.onWindowFocusChanged(hasFocus)

    if (hasFocus && !animationStarted) {
      startAnimation()
    }
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    presenter.onActivityResult(requestCode, data)
  }

  override fun showLoading() {
    startButton.gone()
    loadingView.visible()
  }

  override fun hideLoading() {
    loadingView.gone()
    startButton.visible()
  }

  override fun navigateToNextScreen() {
    navigator.goToInitialWizard(this)
    finish()
  }

  override fun showLoginError() {
    makeSnackbar(R.string.unable_to_make_cloud_login, Snackbar.LENGTH_LONG)
  }

  private fun initializeInjector() {
    component.inject(this)
  }

  private fun startAnimation() {
    val translationY = (logoPlaceholder.y + container.y) - logoImageView.y

    ViewCompat.animate(logoImageView)
        .translationY(translationY)
        .setStartDelay(STARTUP_DELAY)
        .setDuration(LOGO_DURATION)
        .start()

    ViewCompat.animate(title)
        .alpha(1f)
        .setStartDelay(STARTUP_DELAY + LOGO_DURATION)
        .setDuration(TEXT_DURATION)
        .start()

    ViewCompat.animate(bodyFirst)
        .alpha(1f)
        .setStartDelay(STARTUP_DELAY + LOGO_DURATION + ITEM_DELAY)
        .setDuration(TEXT_DURATION)
        .start()

    ViewCompat.animate(bodySecond)
        .alpha(1f)
        .setStartDelay(STARTUP_DELAY + LOGO_DURATION + ITEM_DELAY * 2)
        .setDuration(TEXT_DURATION)
        .start()

    ViewCompat.animate(startButton)
        .alpha(1f)
        .setStartDelay(STARTUP_DELAY + LOGO_DURATION + ITEM_DELAY * 3)
        .setDuration(BUTTON_DURATION)
        .start()

    ViewCompat.animate(whatIsButton)
        .alpha(1f)
        .setStartDelay(STARTUP_DELAY + LOGO_DURATION + ITEM_DELAY * 4)
        .setDuration(BUTTON_DURATION)
        .start()

    animationStarted = true
  }
}
