package com.arturogutierrez.openticator.storage.realm.mapper

import com.arturogutierrez.openticator.domain.category.model.Category
import com.arturogutierrez.openticator.storage.realm.model.CategoryRealm
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.runners.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CategoryRealmMapperTest {

  companion object {
    private val FAKE_ID = "id"
    private val FAKE_NAME = "name"
  }

  private lateinit var categoryRealmMapper: CategoryRealmMapper

  @Before
  fun setUp() {
    categoryRealmMapper = CategoryRealmMapper()
  }

  @Test
  fun testCategoryToRealm() {
    val category = Category(FAKE_ID, FAKE_NAME)

    val categoryRealm = categoryRealmMapper.transform(category)

    assertThat(categoryRealm.categoryId, `is`(FAKE_ID))
    assertThat(categoryRealm.name, `is`(FAKE_NAME))
  }

  @Test
  fun testCategoryRealmToCategory() {
    val categoryRealm = CategoryRealm()
    categoryRealm.categoryId = FAKE_ID
    categoryRealm.name = FAKE_NAME

    val category = categoryRealmMapper.reverseTransform(categoryRealm)

    assertThat(category.categoryId, `is`(FAKE_ID))
    assertThat(category.name, `is`(FAKE_NAME))
  }
}
