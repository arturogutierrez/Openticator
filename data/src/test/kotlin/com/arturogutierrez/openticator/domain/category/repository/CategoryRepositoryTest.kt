package com.arturogutierrez.openticator.domain.category.repository

import com.arturogutierrez.openticator.domain.category.model.Category
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CategoryRepositoryTest {

  private lateinit var categoryRepository: CategoryRepository
  @Mock
  private lateinit var mockCategoryDataStore: CategoryDataStore

  @Before
  fun setUp() {
    categoryRepository = CategoryRepositoryImpl(mockCategoryDataStore)
  }

  @Test
  fun testAddCategory() {
    val category = Category("id", "name")

    categoryRepository.add(category)

    verify<CategoryDataStore>(mockCategoryDataStore).add(category)
  }

  @Test
  fun testGetCategories() {
    categoryRepository.categories

    verify<CategoryDataStore>(mockCategoryDataStore).categories
  }
}
