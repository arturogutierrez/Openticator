package com.arturogutierrez.openticator.domain.bootstrap

import com.arturogutierrez.openticator.domain.bootstrap.InitialScreenSelector.InitialScreen.LOGIN
import com.arturogutierrez.openticator.domain.bootstrap.InitialScreenSelector.InitialScreen.MAIN_SCREEN
import com.arturogutierrez.openticator.storage.preferences.Preferences
import javax.inject.Inject

class InitialScreenSelector @Inject constructor(private val preferences: Preferences) {

  enum class InitialScreen {
    LOGIN, MAIN_SCREEN
  }

  fun initialScreen(): InitialScreen {
    if (isAppConfigured()) {
      return MAIN_SCREEN
    }

    return LOGIN
  }

  private fun isAppConfigured() = preferences.masterPassword != null
}
