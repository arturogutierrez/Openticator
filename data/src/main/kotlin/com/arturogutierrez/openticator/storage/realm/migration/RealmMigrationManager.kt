package com.arturogutierrez.openticator.storage.realm.migration

import com.arturogutierrez.openticator.storage.realm.migration.RealmMigrationManager.Schemas.Initial
import com.arturogutierrez.openticator.storage.realm.migration.RealmMigrationManager.Schemas.Synchronizable
import io.realm.DynamicRealm
import io.realm.RealmMigration

class RealmMigrationManager : RealmMigration {

  enum class Schemas(val version: Long) {
    Initial(0L), Synchronizable(1L)
  }

  companion object {
    val currentSchema = Synchronizable.version
  }

  private val migrationSteps = listOf(
      CloudSynchronizableMigrationStep(Initial.version, Synchronizable.version)
  )

  override fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {
    val migrations = migrationSteps.associateBy { it.fromSchemaVersion }
    var currentVersion = oldVersion

    while (currentVersion != newVersion) {
      val migrationStep = migrations[currentVersion] ?: throw IllegalArgumentException("Realm migration step not found for schema version $currentVersion")

      migrationStep.migrate(realm)
      currentVersion = migrationStep.toSchemaVersion
    }
  }
}
