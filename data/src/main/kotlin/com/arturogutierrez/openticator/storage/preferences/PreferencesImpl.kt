package com.arturogutierrez.openticator.storage.preferences

import android.content.SharedPreferences
import javax.inject.Inject

class PreferencesImpl @Inject constructor(val sharedPreferences: SharedPreferences) : Preferences {

  private companion object {
    val MASTER_PASSWORD_ID = "MASTER_ID"
  }

  override fun reset() {
    sharedPreferences.edit().clear().apply()
  }

  override var masterPassword: String?
    get() = sharedPreferences.getString(MASTER_PASSWORD_ID, null)
    set(masterPassword) = sharedPreferences.edit().putString(MASTER_PASSWORD_ID, masterPassword).apply()
}
