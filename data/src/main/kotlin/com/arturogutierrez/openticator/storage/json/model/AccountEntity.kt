package com.arturogutierrez.openticator.storage.json.model

import com.google.gson.annotations.SerializedName

data class AccountEntity(@SerializedName("id") val accountId: String,
                         @SerializedName("name") val name: String,
                         @SerializedName("type") val type: String,
                         @SerializedName("secret") val secret: String,
                         @SerializedName("issuer") val issuer: String,
                         @SerializedName("order") val order: Int,
                         @SerializedName("category_id") val categoryId: String)
