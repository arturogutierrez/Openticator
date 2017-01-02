package com.arturogutierrez.openticator.welcome

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.matcher.IntentMatchers
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers
import com.arturogutierrez.openticator.R
import com.arturogutierrez.openticator.domain.password.wizard.activity.MasterPasswordActivity
import com.arturogutierrez.openticator.domain.welcome.activity.WelcomeActivity
import org.junit.Rule
import org.junit.Test

class WelcomeTest {

  @Rule
  @JvmField
  val activityRule = IntentsTestRule(WelcomeActivity::class.java, true)

  @Test
  fun writingWeakPasswordDoesNotAllowToEnterInTheApp() {
    onView(ViewMatchers.withId(R.id.start_btn)).perform(ViewActions.click())

    Intents.intended(IntentMatchers.hasComponent(MasterPasswordActivity::class.java.name))
  }
}
