package com.arturogutierrez.openticator.storage.json.model

import com.google.gson.annotations.SerializedName

data class CategoryEntity(@SerializedName("id") val categoryId: String,
                          @SerializedName("name") val name: String)
