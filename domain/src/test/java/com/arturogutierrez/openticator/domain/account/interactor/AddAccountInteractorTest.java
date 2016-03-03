package com.arturogutierrez.openticator.domain.account.interactor;

import com.arturogutierrez.openticator.domain.account.AccountFactory;
import com.arturogutierrez.openticator.domain.account.model.Account;
import com.arturogutierrez.openticator.domain.account.model.OTPType;
import com.arturogutierrez.openticator.domain.account.repository.AccountRepository;
import com.arturogutierrez.openticator.domain.category.CategoryFactory;
import com.arturogutierrez.openticator.domain.category.CategorySelector;
import com.arturogutierrez.openticator.domain.category.repository.CategoryRepository;
import com.arturogutierrez.openticator.domain.issuer.model.Issuer;
import com.arturogutierrez.openticator.executor.PostExecutionThread;
import com.arturogutierrez.openticator.executor.ThreadExecutor;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rx.observers.TestSubscriber;
import rx.schedulers.TestScheduler;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class AddAccountInteractorTest {

  @Mock
  private AccountRepository mockAccountRepository;
  @Mock
  private AccountFactory mockAccountFactory;
  @Mock
  private CategoryRepository mockCategoryRepository;
  @Mock
  private ThreadExecutor mockThreadExecutor;
  @Mock
  private PostExecutionThread mockPostExecutionThread;

  private AddAccountInteractor addAccountInteractor;

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);

    addAccountInteractor =
        new AddAccountInteractor(mockAccountRepository, mockAccountFactory, mockCategoryRepository,
            new CategorySelector(), new CategoryFactory(), mockThreadExecutor,
            mockPostExecutionThread);
  }

  @Test
  public void testAddNewAccount() {
    Account account = new Account("id", "name", OTPType.TOTP, "secret", Issuer.UNKNOWN);
    when(mockAccountFactory.createAccount(anyString(), anyString())).thenReturn(account);
    TestSubscriber<Account> testSubscriber = new TestSubscriber<>();

    addAccountInteractor.configure("name", "secret");
    addAccountInteractor.createObservable().subscribe(testSubscriber);

    verify(mockAccountRepository).add(account);
    verifyZeroInteractions(mockThreadExecutor);
    verifyZeroInteractions(mockPostExecutionThread);
  }

  @Test
  public void testExceptionRaisedWhenAddNewAccountWithNoConfiguration() {
    expectedException.expect(IllegalStateException.class);

    addAccountInteractor.createObservable();
  }
}
