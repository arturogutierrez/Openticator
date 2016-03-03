package com.arturogutierrez.openticator.domain.category.repository;

import com.arturogutierrez.openticator.domain.account.model.Account;
import com.arturogutierrez.openticator.domain.category.model.Category;
import java.util.List;
import rx.Observable;

public interface CategoryDataStore {

  Observable<Category> add(Category category);

  Observable<Category> addAccount(Category category, Account account);

  Observable<List<Category>> getCategories();
}
