package com.arturogutierrez.openticator.storage.realm.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class CategoryRealm : RealmObject() {

    @PrimaryKey
    open var categoryId: String = ""
    open var name: String = ""
}
