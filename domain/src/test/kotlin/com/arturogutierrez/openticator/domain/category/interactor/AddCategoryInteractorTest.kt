package com.arturogutierrez.openticator.domain.category.interactor

import com.arturogutierrez.openticator.domain.account.model.Account
import com.arturogutierrez.openticator.domain.account.model.OTPType
import com.arturogutierrez.openticator.domain.category.CategoryFactory
import com.arturogutierrez.openticator.domain.category.interactor.AddCategoryInteractor.Params
import com.arturogutierrez.openticator.domain.category.model.Category
import com.arturogutierrez.openticator.domain.category.repository.CategoryRepository
import com.arturogutierrez.openticator.domain.issuer.model.Issuer
import com.arturogutierrez.openticator.executor.PostExecutionThread
import com.arturogutierrez.openticator.executor.ThreadExecutor
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class AddCategoryInteractorTest {

  @Mock
  private lateinit var mockCategoryRepository: CategoryRepository
  @Mock
  private lateinit var mockCategoryFactory: CategoryFactory
  @Mock
  private lateinit var mockThreadExecutor: ThreadExecutor
  @Mock
  private lateinit var mockPostExecutionThread: PostExecutionThread

  private lateinit var addCategoryInteractor: AddCategoryInteractor

  @Before
  fun setUp() {
    MockitoAnnotations.initMocks(this)

    addCategoryInteractor = AddCategoryInteractor(mockCategoryRepository, mockCategoryFactory, mockThreadExecutor,
        mockPostExecutionThread)
  }

  @Test
  fun testAddNewCategory() {
    val account = Account("id", "name", OTPType.TOTP, "secret", Issuer.UNKNOWN)
    val category = Category("id", "name")
    whenever(mockCategoryFactory.createCategory(anyString())).thenReturn(category)
    whenever(mockCategoryRepository.add(category)).thenReturn(Single.just(category))

    addCategoryInteractor.buildObservable(Params("name", account))

    verify(mockCategoryRepository).add(category)
    verifyZeroInteractions(mockThreadExecutor)
    verifyZeroInteractions(mockPostExecutionThread)
  }
}
