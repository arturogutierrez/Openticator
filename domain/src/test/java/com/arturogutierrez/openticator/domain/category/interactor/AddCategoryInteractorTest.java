package com.arturogutierrez.openticator.domain.category.interactor;

import com.arturogutierrez.openticator.domain.account.model.Account;
import com.arturogutierrez.openticator.domain.account.model.OTPType;
import com.arturogutierrez.openticator.domain.category.CategoryFactory;
import com.arturogutierrez.openticator.domain.category.model.Category;
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
import rx.Observable;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class AddCategoryInteractorTest {

  @Mock
  private CategoryRepository mockCategoryRepository;
  @Mock
  private CategoryFactory mockCategoryFactory;
  @Mock
  private ThreadExecutor mockThreadExecutor;
  @Mock
  private PostExecutionThread mockPostExecutionThread;

  private AddCategoryInteractor addCategoryInteractor;

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);

    addCategoryInteractor =
        new AddCategoryInteractor(mockCategoryRepository, mockCategoryFactory, mockThreadExecutor,
            mockPostExecutionThread);
  }

  @Test
  public void testAddNewCategory() {
    Account account = new Account("id", "name", OTPType.TOTP, "secret", Issuer.UNKNOWN);
    Category category = new Category("id", "name");
    when(mockCategoryFactory.createCategory(anyString())).thenReturn(category);
    when(mockCategoryRepository.add(category)).thenReturn(Observable.just(category));

    addCategoryInteractor.configure("name", account);
    addCategoryInteractor.createObservable();

    verify(mockCategoryRepository).add(category);
    verifyZeroInteractions(mockThreadExecutor);
    verifyZeroInteractions(mockPostExecutionThread);
  }

  @Test
  public void testExceptionRaisedWhenAddNewAccountWithNoConfiguration() {
    expectedException.expect(IllegalStateException.class);

    addCategoryInteractor.createObservable();
  }
}
