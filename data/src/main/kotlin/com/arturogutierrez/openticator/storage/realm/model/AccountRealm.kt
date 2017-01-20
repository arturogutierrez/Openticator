package com.arturogutierrez.openticator.storage.realm.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

@RealmModel
class AccountRealm : RealmObject() {

  companion object {
    val TOTP_TYPE = "TOTP"
    val HOTP_TYPE = "HOTP"
  }

  @PrimaryKey
  var accountId: String = ""
  var name: String = ""
  var type: String = ""
  var secret: String = ""
  var issuer: String = ""
  var order: Int = 0
  var pendingToSync: Boolean = true
  var category: CategoryRealm? = null
}
