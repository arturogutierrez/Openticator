package com.arturogutierrez.openticator.domain.account.interactor

import com.arturogutierrez.openticator.domain.account.interactor.InteractorTest.TestInteractor.EmptyParams
import com.arturogutierrez.openticator.executor.PostExecutionThread
import com.arturogutierrez.openticator.executor.ThreadExecutor
import com.arturogutierrez.openticator.interactor.Interactor
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import rx.Observable
import rx.observers.TestSubscriber
import rx.schedulers.TestScheduler

class InteractorTest {

  @Mock
  private lateinit var mockThreadExecutor: ThreadExecutor
  @Mock
  private lateinit var mockPostExecutionThread: PostExecutionThread
  private lateinit var interactor: TestInteractor

  @Before
  fun setUp() {
    MockitoAnnotations.initMocks(this)

    interactor = TestInteractor(mockThreadExecutor, mockPostExecutionThread)
  }

  @Test
  fun testExecuteReturnResults() {
    val testSubscriber = TestSubscriber<String>()
    val testScheduler = TestScheduler()
    given(mockPostExecutionThread.scheduler).willReturn(testScheduler)

    interactor.execute(EmptyParams, testSubscriber)

    assertThat(testSubscriber.onNextEvents.size, `is`(0))
  }

  @Test
  fun testUnsubscription() {
    val testSubscriber = TestSubscriber<String>()

    interactor.execute(EmptyParams, testSubscriber)
    interactor.unsubscribe()

    assertThat(testSubscriber.isUnsubscribed, `is`(true))
  }

  class TestInteractor(threadExecutor: ThreadExecutor, postExecutionThread: PostExecutionThread) : Interactor<EmptyParams, String>(threadExecutor, postExecutionThread) {

    override fun createObservable(params: EmptyParams): Observable<String> {
      return Observable.empty<String>()
    }

    object EmptyParams
  }
}
