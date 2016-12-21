package com.arturogutierrez.openticator.storage.json.model

import com.google.gson.annotations.SerializedName

data class BackupEntity(@SerializedName("version") val version: Int,
                        @SerializedName("categories") val categories: List<CategoryEntity>,
                        @SerializedName("accounts") val accounts: List<AccountEntity>)
