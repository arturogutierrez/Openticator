package com.arturogutierrez.openticator.domain.interactor;

import com.arturogutierrez.openticator.domain.account.interactor.GetAccountsInteractor;
import com.arturogutierrez.openticator.executor.PostExecutionThread;
import com.arturogutierrez.openticator.executor.ThreadExecutor;
import com.arturogutierrez.openticator.domain.account.repository.AccountRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

public class GetAccountsInteractorTest {

  @Mock
  private ThreadExecutor mockThreadExecutor;
  @Mock
  private AccountRepository mockAccountRepository;
  @Mock
  private PostExecutionThread mockPostExecutionThread;

  private GetAccountsInteractor getAccountsInteractor;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);

    getAccountsInteractor =
        new GetAccountsInteractor(mockAccountRepository, mockThreadExecutor, mockPostExecutionThread);
  }

  @Test
  public void testUsingAccountsFromRepository() {
    getAccountsInteractor.createObservable();

    verify(mockAccountRepository).getAccounts();
    verifyZeroInteractions(mockThreadExecutor);
    verifyZeroInteractions(mockPostExecutionThread);
  }
}
