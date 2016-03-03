package com.arturogutierrez.openticator.domain.category.repository;

import com.arturogutierrez.openticator.domain.account.model.Account;
import com.arturogutierrez.openticator.domain.category.model.Category;
import com.arturogutierrez.openticator.storage.CategoryDiskDataStore;
import java.util.List;
import javax.inject.Inject;
import rx.Observable;

public class CategoryRepositoryImpl implements CategoryRepository {

  private final CategoryDataStore categoryDataStore;

  @Inject
  public CategoryRepositoryImpl(CategoryDiskDataStore categoryDiskDataStore) {
    this.categoryDataStore = categoryDiskDataStore;
  }

  @Override
  public Observable<Category> add(Category category) {
    return categoryDataStore.add(category);
  }

  @Override
  public Observable<Category> addAccount(Category category, Account account) {
    return categoryDataStore.addAccount(category, account);
  }

  @Override
  public Observable<List<Category>> getCategories() {
    return categoryDataStore.getCategories();
  }
}
