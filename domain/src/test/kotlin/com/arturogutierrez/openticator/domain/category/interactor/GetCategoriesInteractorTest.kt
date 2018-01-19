package com.arturogutierrez.openticator.domain.category.interactor

import com.arturogutierrez.openticator.domain.category.interactor.GetCategoriesInteractor.EmptyParams
import com.arturogutierrez.openticator.domain.category.repository.CategoryRepository
import com.arturogutierrez.openticator.executor.PostExecutionThread
import com.arturogutierrez.openticator.executor.ThreadExecutor
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyZeroInteractions
import org.mockito.MockitoAnnotations

class GetCategoriesInteractorTest {

  @Mock
  private lateinit var mockCategoryRepository: CategoryRepository
  @Mock
  private lateinit var mockThreadExecutor: ThreadExecutor
  @Mock
  private lateinit var mockPostExecutionThread: PostExecutionThread

  private lateinit var getCategoryInteractor: GetCategoriesInteractor

  @Before
  fun setUp() {
    MockitoAnnotations.initMocks(this)

    getCategoryInteractor = GetCategoriesInteractor(mockCategoryRepository, mockThreadExecutor,
        mockPostExecutionThread)
  }

  @Test
  fun testGettingAccountsFromRepository() {
    getCategoryInteractor.buildObservable(EmptyParams)

    verify<CategoryRepository>(mockCategoryRepository).categories
    verifyZeroInteractions(mockThreadExecutor)
    verifyZeroInteractions(mockPostExecutionThread)
  }
}
