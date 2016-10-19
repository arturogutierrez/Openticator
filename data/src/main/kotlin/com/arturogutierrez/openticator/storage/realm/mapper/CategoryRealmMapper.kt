package com.arturogutierrez.openticator.storage.realm.mapper

import com.arturogutierrez.openticator.domain.category.model.Category
import com.arturogutierrez.openticator.mapper.Mapper
import com.arturogutierrez.openticator.storage.realm.model.CategoryRealm
import javax.inject.Inject

class CategoryRealmMapper @Inject constructor() : Mapper<Category, CategoryRealm>() {

    override fun transform(value: Category): CategoryRealm {
        val categoryRealm = CategoryRealm()
        categoryRealm.categoryId = value.categoryId
        categoryRealm.name = value.name
        return categoryRealm
    }

    override fun reverseTransform(value: CategoryRealm): Category {
        return Category(value.categoryId, value.name)
    }
}
