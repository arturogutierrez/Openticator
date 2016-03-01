package com.arturogutierrez.openticator.storage;

import com.arturogutierrez.openticator.domain.account.model.Account;
import com.arturogutierrez.openticator.domain.category.model.Category;
import com.arturogutierrez.openticator.domain.category.repository.CategoryDataStore;
import com.arturogutierrez.openticator.storage.realm.mapper.CategoryRealmMapper;
import com.arturogutierrez.openticator.storage.realm.model.CategoryRealm;
import io.realm.Realm;
import io.realm.RealmResults;
import java.util.List;
import javax.inject.Inject;
import rx.Observable;

public class CategoryDiskDataStore implements CategoryDataStore {

  private final CategoryRealmMapper categoryRealmMapper;

  @Inject
  public CategoryDiskDataStore(CategoryRealmMapper categoryRealmMapper) {
    this.categoryRealmMapper = categoryRealmMapper;
  }

  @Override
  public Observable<Category> add(Category category) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public Observable<Category> addAccount(Category category, Account account) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public Observable<List<Category>> getCategories() {
    Observable<RealmResults<CategoryRealm>> getCategoriesObservable =
        Observable.create(subscriber -> {
          Realm realm = Realm.getDefaultInstance();
          realm.refresh();

          RealmResults<CategoryRealm> results =
              realm.where(CategoryRealm.class).findAllSorted("name");
          subscriber.onNext(results);
          subscriber.onCompleted();
        });
    return getCategoriesObservable.map(categoryRealmMapper::reverseTransform);
  }
}
