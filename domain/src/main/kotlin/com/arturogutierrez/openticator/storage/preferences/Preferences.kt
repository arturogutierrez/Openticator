package com.arturogutierrez.openticator.storage.preferences

interface Preferences {
  companion object {
    val preferencesName = "app_prefs"
  }

  fun reset()

  var masterPassword: String?
}
