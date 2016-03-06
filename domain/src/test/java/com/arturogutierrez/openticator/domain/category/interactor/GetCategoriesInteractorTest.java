package com.arturogutierrez.openticator.domain.category.interactor;

import com.arturogutierrez.openticator.domain.category.model.Category;
import com.arturogutierrez.openticator.domain.category.repository.CategoryRepository;
import com.arturogutierrez.openticator.executor.PostExecutionThread;
import com.arturogutierrez.openticator.executor.ThreadExecutor;
import java.util.List;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rx.Observable;

import static org.mockito.BDDMockito.given;
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
    given(mockCategoryRepository.getCategories()).willReturn(Observable.<List<Category>>empty());

    getCategoryInteractor.createObservable();

    verify(mockCategoryRepository).getCategories();
    verifyZeroInteractions(mockThreadExecutor);
    verifyZeroInteractions(mockPostExecutionThread);
  }
}
