package com.arturogutierrez.openticator.domain.welcome.activity

import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.arturogutierrez.openticator.R
import com.arturogutierrez.openticator.application.OpenticatorApplication
import com.arturogutierrez.openticator.domain.navigator.Navigator
import com.arturogutierrez.openticator.helpers.find
import javax.inject.Inject

class WelcomeActivity : AppCompatActivity() {

  companion object Animation {
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
  private val startButton by lazy { find<Button>(R.id.start_btn) }
  private var animationStarted = false

  @Inject
  lateinit var navigator: Navigator

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_welcome)

    initializeInjector()

    startButton.setOnClickListener {
      navigator.goToInitialWizard(this)
      finish()
    }
  }

  override fun onWindowFocusChanged(hasFocus: Boolean) {
    super.onWindowFocusChanged(hasFocus)

    if (hasFocus && !animationStarted) {
      startAnimation()
    }
  }

  private fun initializeInjector() {
    (application as OpenticatorApplication).applicationComponent.inject(this)
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

    animationStarted = true
  }
}
