package com.arturogutierrez.openticator.domain.category.interactor;

import com.arturogutierrez.openticator.domain.account.model.Account;
import com.arturogutierrez.openticator.domain.category.CategoryFactory;
import com.arturogutierrez.openticator.domain.category.model.Category;
import com.arturogutierrez.openticator.domain.category.repository.CategoryRepository;
import com.arturogutierrez.openticator.executor.PostExecutionThread;
import com.arturogutierrez.openticator.executor.ThreadExecutor;
import com.arturogutierrez.openticator.interactor.Interactor;
import rx.Observable;
import rx.functions.Func1;

public class AddCategoryInteractor extends Interactor<Category> {

  private final CategoryRepository categoryRepository;
  private final CategoryFactory categoryFactory;

  private Category newCategory;
  private Account accountToAddToCategory;

  public AddCategoryInteractor(CategoryRepository categoryRepository,
      CategoryFactory categoryFactory, ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.categoryRepository = categoryRepository;
    this.categoryFactory = categoryFactory;
  }

  public void configure(String categoryName, Account account) {
    this.newCategory = categoryFactory.createCategory(categoryName);
    this.accountToAddToCategory = account;
  }

  @Override
  public Observable<Category> createObservable() {
    if (accountToAddToCategory == null) {
      throw new IllegalStateException("You must call configure before execute the interactor");
    }

    return categoryRepository.add(newCategory).flatMap(new Func1<Category, Observable<Category>>() {
      @Override
      public Observable<Category> call(Category category) {
        return categoryRepository.addAccount(category, accountToAddToCategory);
      }
    });
  }
}
