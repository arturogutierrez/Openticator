package com.arturogutierrez.openticator.domain.category.interactor;

import com.arturogutierrez.openticator.domain.category.repository.CategoryRepository;
import com.arturogutierrez.openticator.executor.PostExecutionThread;
import com.arturogutierrez.openticator.executor.ThreadExecutor;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

public class GetCategoriesInteractorTest {

  @Mock
  private CategoryRepository mockCategoryRepository;
  @Mock
  private ThreadExecutor mockThreadExecutor;
  @Mock
  private PostExecutionThread mockPostExecutionThread;

  private GetCategoriesInteractor getCategoryInteractor;

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);

    getCategoryInteractor = new GetCategoriesInteractor(mockCategoryRepository, mockThreadExecutor,
        mockPostExecutionThread);
  }

  @Test
  public void testGettingAccountsFromRepository() {
    getCategoryInteractor.createObservable();

    verify(mockCategoryRepository).getCategories();
    verifyZeroInteractions(mockThreadExecutor);
    verifyZeroInteractions(mockPostExecutionThread);
  }
}
