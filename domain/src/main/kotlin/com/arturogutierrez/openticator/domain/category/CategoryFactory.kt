package com.arturogutierrez.openticator.domain.category

import com.arturogutierrez.openticator.domain.category.model.Category
import java.util.*
import javax.inject.Inject

class CategoryFactory @Inject constructor() {

  fun createCategory(categoryName: String): Category {
    val categoryId = UUID.randomUUID().toString()
    return Category(categoryId, categoryName)
  }

  fun createEmptyCategory(): Category {
    return Category.empty
  }
}
