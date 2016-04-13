package com.arturogutierrez.openticator.storage;

import com.arturogutierrez.openticator.domain.account.model.Account;
import com.arturogutierrez.openticator.domain.category.model.Category;
import com.arturogutierrez.openticator.domain.category.repository.CategoryDataStore;
import com.arturogutierrez.openticator.storage.realm.mapper.CategoryRealmMapper;
import com.arturogutierrez.openticator.storage.realm.model.AccountRealm;
import com.arturogutierrez.openticator.storage.realm.model.CategoryRealm;
import io.realm.Realm;
import io.realm.RealmResults;
import java.util.List;
import javax.inject.Inject;
import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

public class CategoryDiskDataStore implements CategoryDataStore {

  private final CategoryRealmMapper categoryRealmMapper;
  private final Subject<Void, Void> changesPublishSubject;

  @Inject
  public CategoryDiskDataStore(CategoryRealmMapper categoryRealmMapper) {
    this.categoryRealmMapper = categoryRealmMapper;
    this.changesPublishSubject = PublishSubject.<Void>create().toSerialized();
  }

  @Override
  public Observable<Category> add(Category category) {
    Observable<Category> categoryObservable = Observable.fromCallable(() -> {
      CategoryRealm categoryRealm = categoryRealmMapper.transform(category);

      Realm defaultRealm = Realm.getDefaultInstance();
      defaultRealm.executeTransaction(realm -> realm.copyToRealm(categoryRealm));

      return category;
    });

    return categoryObservable.doOnNext(categoryAdded -> notifyAccountChanges());
  }

  @Override
  public Observable<Category> addAccount(Category category, Account account) {
    return Observable.fromCallable(() -> {
      Realm defaultRealm = Realm.getDefaultInstance();
      defaultRealm.executeTransaction(realm -> {
        CategoryRealm categoryRealm = getCategoryAsBlocking(realm, category.getCategoryId());
        AccountRealm accountRealm = getAccountAsBlocking(realm, account.getAccountId());
        if (categoryRealm == null || accountRealm == null) {
          return;
        }

        accountRealm.setCategory(categoryRealm);
      });

      return category;
    });
  }

  @Override
  public Observable<List<Category>> getCategories() {
    return changesPublishSubject.map(aVoid -> getCategoriesAsBlocking())
        .startWith(Observable.just(getCategoriesAsBlocking()));
  }

  private List<Category> getCategoriesAsBlocking() {
    Realm realm = Realm.getDefaultInstance();
    realm.refresh();
    RealmResults<CategoryRealm> realmResults =
        realm.where(CategoryRealm.class).findAllSorted("name");
    return categoryRealmMapper.reverseTransform(realmResults);
  }

  private CategoryRealm getCategoryAsBlocking(Realm realm, String categoryId) {
    return realm.where(CategoryRealm.class).equalTo("categoryId", categoryId).findFirst();
  }

  private AccountRealm getAccountAsBlocking(Realm realm, String accountId) {
    return realm.where(AccountRealm.class).equalTo("accountId", accountId).findFirst();
  }

  private void notifyAccountChanges() {
    changesPublishSubject.onNext(null);
  }
}
