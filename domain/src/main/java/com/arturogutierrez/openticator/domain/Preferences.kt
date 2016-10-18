package com.arturogutierrez.openticator.domain

interface Preferences {
    companion object {
        val preferencesName = "app_prefs"
    }

    fun reset()

    var masterPassword: String
}
