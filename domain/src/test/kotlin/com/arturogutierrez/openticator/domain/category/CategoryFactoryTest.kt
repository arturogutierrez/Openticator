package com.arturogutierrez.openticator.domain.category

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

class CategoryFactoryTest {

  private lateinit var categoryFactory: CategoryFactory

  @Before
  fun setUp() {
    categoryFactory = CategoryFactory()
  }

  @Test
  fun testCreatingCategory() {
    val category = categoryFactory.createCategory("name")

    assertThat(category.categoryId, `is`(notNullValue()))
    assertThat(category.name, `is`("name"))
  }
}
