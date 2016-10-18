package com.arturogutierrez.openticator.domain.bootstrap

import com.arturogutierrez.openticator.domain.Preferences
import javax.inject.Inject

class InitialScreenSelector @Inject constructor(private val preferences: Preferences) {

    fun shouldShowWizard(): Boolean {
        return preferences.masterPassword == null
    }
}
