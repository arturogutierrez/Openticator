package com.arturogutierrez.openticator.storage.realm.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class AccountRealm : RealmObject() {

  companion object {
    val TOTP_TYPE = "TOTP"
    val HOTP_TYPE = "HOTP"
  }

  @PrimaryKey
  open var accountId: String = ""
  open var name: String = ""
  open var type: String = ""
  open var secret: String = ""
  open var issuer: String = ""
  open var order: Int = 0
  open var category: CategoryRealm? = null
}
