package com.arturogutierrez.openticator.storage

import android.content.Context
import android.util.Base64
import com.arturogutierrez.openticator.domain.DatabaseConfigurator
import io.realm.Realm
import io.realm.RealmConfiguration
import javax.inject.Inject

class RealmDatabaseConfigurator @Inject constructor(private val context: Context) : DatabaseConfigurator {

  override fun configure(passwordInBase64: String) {
    val encryptionKey = Base64.decode(passwordInBase64, Base64.NO_WRAP)

    configureRealm(encryptionKey)
  }

  private fun configureRealm(encryptionKey: ByteArray) {
    Realm.init(context)
    val realmConfiguration = RealmConfiguration.Builder().encryptionKey(encryptionKey).build()
    Realm.setDefaultConfiguration(realmConfiguration)
  }
}
