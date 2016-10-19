package com.arturogutierrez.openticator.domain.category.model

data class Category(val categoryId: String, val name: String) {
    companion object {
        val empty = Category("emptyCategory", "emptyCategory")
    }
}
