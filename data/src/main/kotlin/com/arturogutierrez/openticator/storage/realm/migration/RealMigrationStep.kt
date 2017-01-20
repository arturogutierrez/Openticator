package com.arturogutierrez.openticator.storage.realm.migration

import io.realm.DynamicRealm

interface RealMigrationStep {

  val fromSchemaVersion: Long

  val toSchemaVersion: Long

  fun migrate(dynamicRealm: DynamicRealm)
}
