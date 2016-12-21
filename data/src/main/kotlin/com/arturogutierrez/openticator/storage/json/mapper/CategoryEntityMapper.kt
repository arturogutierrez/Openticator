package com.arturogutierrez.openticator.storage.json.mapper

import com.arturogutierrez.openticator.domain.category.model.Category
import com.arturogutierrez.openticator.mapper.Mapper
import com.arturogutierrez.openticator.storage.json.model.CategoryEntity
import javax.inject.Inject

class CategoryEntityMapper @Inject constructor() : Mapper<Category, CategoryEntity> {

  override fun transform(category: Category) = CategoryEntity(category.categoryId, category.name)

  override fun reverseTransform(value: CategoryEntity): Category {
    throw UnsupportedOperationException("not implemented")
  }
}
