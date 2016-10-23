package com.arturogutierrez.openticator.domain.account.interactor

import com.arturogutierrez.openticator.domain.account.model.Account
import com.arturogutierrez.openticator.domain.account.model.AccountPasscode
import com.arturogutierrez.openticator.domain.account.model.OTPType
import com.arturogutierrez.openticator.domain.issuer.model.Issuer
import com.arturogutierrez.openticator.domain.otp.model.Passcode
import com.arturogutierrez.openticator.executor.PostExecutionThread
import com.arturogutierrez.openticator.executor.ThreadExecutor
import com.arturogutierrez.openticator.storage.clipboard.ClipboardRepository
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyZeroInteractions
import org.mockito.MockitoAnnotations
import rx.observers.TestSubscriber

class CopyToClipboardInteractorTest {

  @Mock
  private lateinit var mockClipboardRepository: ClipboardRepository
  @Mock
  private lateinit var mockThreadExecutor: ThreadExecutor
  @Mock
  private lateinit var mockPostExecutionThread: PostExecutionThread

  private lateinit var copyToClipboardInteractor: CopyToClipboardInteractor

  companion object {
    val fakeCode = "CODE"
    val fakeAccountName = "NAME"
  }

  @Before
  fun setUp() {
    MockitoAnnotations.initMocks(this)

    copyToClipboardInteractor = CopyToClipboardInteractor(mockClipboardRepository, mockThreadExecutor,
        mockPostExecutionThread)
  }

  @Test
  fun testAddNewAccount() {
    val account = Account("id", fakeAccountName, OTPType.TOTP, "secret", Issuer.UNKNOWN)
    val passcode = Passcode(fakeCode, 0)
    val accountPasscode = AccountPasscode(account, Issuer.UNKNOWN, passcode)
    val testSubscriber = TestSubscriber<Unit>()

    copyToClipboardInteractor.configure(accountPasscode)
    copyToClipboardInteractor.createObservable().subscribe(testSubscriber)

    verify<ClipboardRepository>(mockClipboardRepository).copy(fakeAccountName, fakeCode)
    verifyZeroInteractions(mockThreadExecutor)
    verifyZeroInteractions(mockPostExecutionThread)
  }
}
