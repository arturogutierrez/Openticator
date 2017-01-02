package com.arturogutierrez.openticator.wizard

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.Intents.times
import android.support.test.espresso.intent.matcher.IntentMatchers
import android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.RootMatchers.withDecorView
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import com.arturogutierrez.openticator.R
import com.arturogutierrez.openticator.domain.account.list.activity.AccountListActivity
import com.arturogutierrez.openticator.domain.password.wizard.activity.MasterPasswordActivity
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class SetupWizardTest {

  companion object Defaults {
    val WEAK_PASSWORD = "1234"
    val WRONG_PASSWORD = "12345"
    val PASSWORD = "1234567890"
  }

  @Rule
  @JvmField
  val activityRule = IntentsTestRule(MasterPasswordActivity::class.java, true)

  @Test
  fun writingWeakPasswordDoesNotAllowToEnterInTheApp() {
    onView(withId(R.id.et_password)).perform(typeText(WEAK_PASSWORD))
    onView(withId(R.id.et_confirm_password)).perform(typeText(WEAK_PASSWORD))

    onView(withId(R.id.action_next)).perform(click())

    intended(hasComponent(AccountListActivity::class.java.name), times(0))
  }

  @Test
  fun writingWeakPasswordShowsAWarning() {
    onView(withId(R.id.et_password)).perform(typeText(WEAK_PASSWORD))
    onView(withId(R.id.et_confirm_password)).perform(typeText(WEAK_PASSWORD))

    onView(withId(R.id.action_next)).perform(click())

    onView(withText(R.string.your_password_is_weak)).inRoot(withDecorView(not(`is`(activityRule.activity.window.decorView)))).check(matches(isDisplayed()))
    intended(hasComponent(AccountListActivity::class.java.name), times(0))
  }

  @Test
  fun writingDifferentPasswordsShowsAWarning() {
    onView(withId(R.id.et_password)).perform(typeText(WEAK_PASSWORD))
    onView(withId(R.id.et_confirm_password)).perform(typeText(WRONG_PASSWORD))

    onView(withId(R.id.action_next)).perform(click())

    onView(withText(R.string.your_password_is_weak)).inRoot(withDecorView(not(`is`(activityRule.activity.window.decorView)))).check(matches(isDisplayed()))
    intended(hasComponent(AccountListActivity::class.java.name), times(0))
  }

  @Test
  fun writingStrongPasswordEntersOnApp() {
    onView(withId(R.id.et_password)).perform(typeText(PASSWORD))
    onView(withId(R.id.et_confirm_password)).perform(typeText(PASSWORD))

    onView(withId(R.id.action_next)).perform(click())

    intended(hasComponent(AccountListActivity::class.java.name))
  }
}
