package com.arturogutierrez.openticator.domain.interactor;

import com.arturogutierrez.openticator.executor.PostExecutionThread;
import com.arturogutierrez.openticator.executor.ThreadExecutor;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rx.Observable;
import rx.observers.TestSubscriber;
import rx.schedulers.TestScheduler;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;

public class InteractorTest {

  @Mock
  private ThreadExecutor mockThreadExecutor;
  @Mock
  private PostExecutionThread mockPostExecutionThread;

  private TestInteractor interactor;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);

    interactor = new TestInteractor(mockThreadExecutor, mockPostExecutionThread);
  }

  @Test
  public void testExecuteReturnResults() {
    TestSubscriber<String> testSubscriber = new TestSubscriber<>();
    TestScheduler testScheduler = new TestScheduler();
    given(mockPostExecutionThread.getScheduler()).willReturn(testScheduler);

    interactor.execute(testSubscriber);

    assertThat(testSubscriber.getOnNextEvents().size(), is(0));
  }

  @Test
  public void testUnsubscription() {
    TestSubscriber<String> testSubscriber = new TestSubscriber<>();

    interactor.execute(testSubscriber);
    interactor.unsubscribe();

    assertThat(testSubscriber.isUnsubscribed(), is(true));
  }

  private static class TestInteractor extends
      com.arturogutierrez.openticator.interactor.Interactor<String> {

    public TestInteractor(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
      super(threadExecutor, postExecutionThread);
    }

    @Override
    public Observable<String> createObservable() {
      return Observable.empty();
    }
  }
}
