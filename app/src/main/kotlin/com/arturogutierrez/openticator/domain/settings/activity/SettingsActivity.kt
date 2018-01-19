package com.arturogutierrez.openticator.domain.settings.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.preference.PreferenceFragmentCompat
import android.support.v7.preference.PreferenceScreen
import com.arturogutierrez.openticator.R
import com.arturogutierrez.openticator.domain.settings.view.SettingsFragment
import com.arturogutierrez.openticator.view.activity.BaseActivity

class SettingsActivity : BaseActivity(R.layout.activity_toolbar), PreferenceFragmentCompat.OnPreferenceStartScreenCallback {

  companion object {
    val PREFERENCE_ROOT = "settings_activity.preference_root"
    val TITLE = "settings_activity.title"
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    initializeActivity(savedInstanceState)
  }

  override fun onPreferenceStartScreen(caller: PreferenceFragmentCompat, preferenceScreen: PreferenceScreen): Boolean {
    val intent = Intent(this, SettingsActivity::class.java).apply {
      putExtra(TITLE, R.string.cloud)
      putExtra(PREFERENCE_ROOT, preferenceScreen.key)
    }
    startActivity(intent)

    return true
  }

  private fun initializeActivity(savedInstanceState: Bundle?) {
    configureToolbar()

    if (savedInstanceState == null) {
      showSettingsFragment()
    }
  }

  private fun configureToolbar() {
    supportActionBar?.apply {
      setTitle(intent.getIntExtra(TITLE, R.string.settings))
      setDisplayHomeAsUpEnabled(true)
    }
  }

  private fun showSettingsFragment() {
    val preferencesRootKey = intent.getStringExtra(PREFERENCE_ROOT)

    val settingsFragment = SettingsFragment().apply {
      arguments = Bundle().apply {
        putString(PreferenceFragmentCompat.ARG_PREFERENCE_ROOT, preferencesRootKey)
      }
    }
    replaceFragment(R.id.content_frame, settingsFragment)
  }
}
