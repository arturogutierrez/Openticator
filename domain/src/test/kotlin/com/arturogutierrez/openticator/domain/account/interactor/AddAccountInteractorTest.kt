package com.arturogutierrez.openticator.domain.account.interactor

import com.arturogutierrez.openticator.domain.account.AccountFactory
import com.arturogutierrez.openticator.domain.account.model.Account
import com.arturogutierrez.openticator.domain.account.model.OTPType
import com.arturogutierrez.openticator.domain.account.repository.AccountRepository
import com.arturogutierrez.openticator.domain.category.CategoryFactory
import com.arturogutierrez.openticator.domain.category.CategorySelector
import com.arturogutierrez.openticator.domain.category.repository.CategoryRepository
import com.arturogutierrez.openticator.domain.issuer.model.Issuer
import com.arturogutierrez.openticator.executor.PostExecutionThread
import com.arturogutierrez.openticator.executor.ThreadExecutor
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import rx.observers.TestSubscriber

class AddAccountInteractorTest {

  @Mock
  private lateinit var mockAccountRepository: AccountRepository
  @Mock
  private lateinit var mockAccountFactory: AccountFactory
  @Mock
  private lateinit var mockCategoryRepository: CategoryRepository
  @Mock
  private lateinit var mockThreadExecutor: ThreadExecutor
  @Mock
  private lateinit var mockPostExecutionThread: PostExecutionThread

  private lateinit var addAccountInteractor: AddAccountInteractor

  @Before
  fun setUp() {
    MockitoAnnotations.initMocks(this)

    addAccountInteractor = AddAccountInteractor(mockAccountRepository, mockAccountFactory, mockCategoryRepository,
        CategorySelector(), CategoryFactory(), mockThreadExecutor,
        mockPostExecutionThread)
  }

  @Test
  fun testAddNewAccount() {
    val account = Account("id", "name", OTPType.TOTP, "secret", Issuer.UNKNOWN)
    `when`(mockAccountFactory.createAccount(anyString(), anyString())).thenReturn(account)
    val testSubscriber = TestSubscriber<Account>()

    addAccountInteractor.configure("name", "secret")
    addAccountInteractor.createObservable().subscribe(testSubscriber)

    verify<AccountRepository>(mockAccountRepository).add(account)
    verifyZeroInteractions(mockThreadExecutor)
    verifyZeroInteractions(mockPostExecutionThread)
  }
}
