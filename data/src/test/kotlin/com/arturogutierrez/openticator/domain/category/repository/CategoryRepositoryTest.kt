package com.arturogutierrez.openticator.domain.category.repository

import com.arturogutierrez.openticator.ApplicationTestCase
import com.arturogutierrez.openticator.domain.category.model.Category
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class CategoryRepositoryTest : ApplicationTestCase() {

  private lateinit var categoryRepository: CategoryRepository
  @Mock
  private lateinit var mockCategoryDataStore: CategoryDataStore

  @Before
  fun setUp() {
    MockitoAnnotations.initMocks(this)

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
