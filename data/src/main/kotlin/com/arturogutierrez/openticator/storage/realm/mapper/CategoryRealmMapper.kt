package com.arturogutierrez.openticator.storage.realm.mapper

import com.arturogutierrez.openticator.domain.category.model.Category
import com.arturogutierrez.openticator.mapper.Mapper
import com.arturogutierrez.openticator.storage.realm.model.CategoryRealm
import javax.inject.Inject

class CategoryRealmMapper @Inject constructor() : Mapper<Category, CategoryRealm> {

  override fun transform(category: Category): CategoryRealm {
    val categoryRealm = CategoryRealm()
    categoryRealm.categoryId = category.categoryId
    categoryRealm.name = category.name
    return categoryRealm
  }

  override fun reverseTransform(categoryRealm: CategoryRealm): Category {
    return Category(categoryRealm.categoryId, categoryRealm.name)
  }
}
