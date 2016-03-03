package com.arturogutierrez.openticator.domain.category.interactor;

import com.arturogutierrez.openticator.domain.account.model.Account;
import com.arturogutierrez.openticator.domain.category.model.Category;
import com.arturogutierrez.openticator.domain.category.repository.CategoryRepository;
import com.arturogutierrez.openticator.executor.PostExecutionThread;
import com.arturogutierrez.openticator.executor.ThreadExecutor;
import com.arturogutierrez.openticator.interactor.Interactor;
import rx.Observable;

public class AddAccountToCategoryInteractor extends Interactor<Category> {

  private final CategoryRepository categoryRepository;

  private Category category;
  private Account accountToAddToCategory;

  public AddAccountToCategoryInteractor(CategoryRepository categoryRepository,
      ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.categoryRepository = categoryRepository;
  }

  public void configure(Category category, Account account) {
    this.category = category;
    this.accountToAddToCategory = account;
  }

  @Override
  public Observable<Category> createObservable() {
    if (accountToAddToCategory == null) {
      throw new IllegalStateException("You must call configure before execute the interactor");
    }

    return categoryRepository.addAccount(category, accountToAddToCategory);
  }
}
