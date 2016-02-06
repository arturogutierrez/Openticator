package com.arturogutierrez.openticator.domain.interactor;

import com.arturogutierrez.openticator.domain.executor.PostExecutionThread;
import com.arturogutierrez.openticator.domain.executor.ThreadExecutor;
import com.arturogutierrez.openticator.domain.repository.AccountRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

public class GetAccountsTest {

  @Mock
  private ThreadExecutor mockThreadExecutor;
  @Mock
  private AccountRepository mockAccountRepository;
  @Mock
  private PostExecutionThread mockPostExecutionThread;

  private GetAccounts getAccounts;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);

    getAccounts =
        new GetAccounts(mockAccountRepository, mockThreadExecutor, mockPostExecutionThread);
  }

  @Test
  public void testUsingAccountsFromRepository() {
    getAccounts.createObservable();

    verify(mockAccountRepository).getAccounts();
    verifyZeroInteractions(mockThreadExecutor);
    verifyZeroInteractions(mockPostExecutionThread);
  }
}
