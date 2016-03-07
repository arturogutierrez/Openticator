package com.arturogutierrez.openticator.domain.category.interactor;

import com.arturogutierrez.openticator.domain.category.model.Category;
import com.arturogutierrez.openticator.domain.category.repository.CategoryRepository;
import com.arturogutierrez.openticator.executor.PostExecutionThread;
import com.arturogutierrez.openticator.executor.ThreadExecutor;
import com.arturogutierrez.openticator.interactor.Interactor;
import java.util.List;
import rx.Observable;

public class GetCategoriesInteractor extends Interactor<List<Category>> {

  private final CategoryRepository categoryRepository;

  public GetCategoriesInteractor(CategoryRepository categoryRepository,
      ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.categoryRepository = categoryRepository;
  }

  @Override
  public Observable<List<Category>> createObservable() {
    return categoryRepository.getCategories().map(this::filterEmptyCategory);
  }

  private List<Category> filterEmptyCategory(List<Category> categories) {
    categories.remove(Category.emptyCategory());
    return categories;
  }
}
