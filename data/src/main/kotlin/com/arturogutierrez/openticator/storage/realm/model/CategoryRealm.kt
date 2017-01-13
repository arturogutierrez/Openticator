package com.arturogutierrez.openticator.storage.realm.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

@RealmModel
class CategoryRealm : RealmObject() {

  @PrimaryKey
  var categoryId: String = ""
  var name: String = ""
}
