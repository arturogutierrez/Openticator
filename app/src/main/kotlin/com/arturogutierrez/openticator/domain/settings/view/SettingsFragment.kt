package com.arturogutierrez.openticator.domain.settings.view

import android.os.Bundle
import android.support.v7.preference.PreferenceFragmentCompat
import com.arturogutierrez.openticator.R

class SettingsFragment : PreferenceFragmentCompat() {

  override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
    setPreferencesFromResource(R.xml.settings, rootKey)
  }
}
