package com.arturogutierrez.openticator.storage.realm

import android.content.Context
import android.util.Base64
import com.arturogutierrez.openticator.storage.database.DatabaseConfigurator
import com.arturogutierrez.openticator.storage.realm.migration.RealmMigrationManager
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
    val realmConfiguration = RealmConfiguration.Builder()
        .encryptionKey(encryptionKey)
        .schemaVersion(RealmMigrationManager.currentSchema)
        .migration(RealmMigrationManager())
        .build()
    Realm.setDefaultConfiguration(realmConfiguration)
  }
}
