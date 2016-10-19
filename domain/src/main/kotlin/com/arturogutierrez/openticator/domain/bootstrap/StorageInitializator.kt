package com.arturogutierrez.openticator.domain.bootstrap

import com.arturogutierrez.openticator.domain.DatabaseConfigurator
import com.arturogutierrez.openticator.domain.Preferences
import javax.inject.Inject

class StorageInitializator @Inject constructor(val preferences: Preferences, val databaseConfigurator: DatabaseConfigurator) {

    fun configureStorage() {
        preferences.masterPassword?.let {
            databaseConfigurator.configure(it)
        }
    }
}
