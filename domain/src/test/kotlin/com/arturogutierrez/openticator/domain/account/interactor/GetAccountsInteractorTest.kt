package com.arturogutierrez.openticator.domain.account.interactor

import com.arturogutierrez.openticator.domain.account.interactor.GetAccountsInteractor.EmptyParams
import com.arturogutierrez.openticator.domain.account.repository.AccountRepository
import com.arturogutierrez.openticator.executor.PostExecutionThread
import com.arturogutierrez.openticator.executor.ThreadExecutor
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyZeroInteractions
import org.mockito.MockitoAnnotations

class GetAccountsInteractorTest {

  @Mock
  private lateinit var mockThreadExecutor: ThreadExecutor
  @Mock
  private lateinit var mockAccountRepository: AccountRepository
  @Mock
  private lateinit var mockPostExecutionThread: PostExecutionThread

  private lateinit var getAccountsInteractor: GetAccountsInteractor

  @Before
  fun setUp() {
    MockitoAnnotations.initMocks(this)

    getAccountsInteractor = GetAccountsInteractor(mockAccountRepository, mockThreadExecutor,
        mockPostExecutionThread)
  }

  @Test
  fun testUsingAccountsFromRepository() {
    getAccountsInteractor.createObservable(EmptyParams)

    verify<AccountRepository>(mockAccountRepository).allAccounts
    verifyZeroInteractions(mockThreadExecutor)
    verifyZeroInteractions(mockPostExecutionThread)
  }
}
