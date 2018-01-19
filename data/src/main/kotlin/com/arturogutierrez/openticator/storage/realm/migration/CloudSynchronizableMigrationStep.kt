package com.arturogutierrez.openticator.storage.realm.migration

import io.realm.DynamicRealm

class CloudSynchronizableMigrationStep(override val fromSchemaVersion: Long,
                                       override val toSchemaVersion: Long) : RealMigrationStep {

  override fun migrate(dynamicRealm: DynamicRealm) {
    val schema = dynamicRealm.schema

    schema["AccountRealm"]?.addField("pendingToSync", Boolean::class.java)
    schema["CategoryRealm"]?.addField("pendingToSync", Boolean::class.java)
  }
}
