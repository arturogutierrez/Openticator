package com.arturogutierrez.openticator.storage;

import com.arturogutierrez.openticator.domain.account.model.Account;
import com.arturogutierrez.openticator.domain.account.repository.AccountDataStore;
import com.arturogutierrez.openticator.domain.category.model.Category;
import com.arturogutierrez.openticator.storage.realm.mapper.AccountRealmMapper;
import com.arturogutierrez.openticator.storage.realm.model.AccountRealm;
import com.arturogutierrez.openticator.storage.realm.model.CategoryRealm;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import java.util.List;
import javax.inject.Inject;
import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

public class AccountDiskDataStore implements AccountDataStore {

  private final AccountRealmMapper accountRealmMapper;
  private final Subject<Void, Void> changesPublishSubject;

  @Inject
  public AccountDiskDataStore(AccountRealmMapper accountRealmMapper) {
    this.accountRealmMapper = accountRealmMapper;
    this.changesPublishSubject = PublishSubject.<Void>create().toSerialized();
  }

  @Override
  public Observable<Account> add(Account account) {
    Observable<Account> accountObservable = getAllAccounts().flatMap(accounts -> {
      int numberOfAccounts = accounts.size();

      return Observable.fromCallable(() -> {
        AccountRealm accountRealm = accountRealmMapper.transform(account);
        accountRealm.setOrder(numberOfAccounts);

        Realm defaultRealm = Realm.getDefaultInstance();
        defaultRealm.executeTransaction(realm -> realm.copyToRealm(accountRealm));

        return account;
      });
    });

    return accountObservable.doOnNext(accountAdded -> notifyAccountChanges());
  }

  @Override
  public Observable<Account> update(Account account) {
    Observable<Account> updateAccountObservable = Observable.fromCallable(() -> {
      Realm defaultRealm = Realm.getDefaultInstance();
      defaultRealm.executeTransaction(realm -> {
        AccountRealm accountRealm = getAccountRealmAsBlocking(realm, account.getAccountId());
        if (accountRealm == null) {
          return;
        }

        accountRealmMapper.copyToAccountRealm(accountRealm, account);
      });

      return account;
    });

    return updateAccountObservable.doOnNext(aVoid -> notifyAccountChanges());
  }

  @Override
  public Observable<Void> remove(Account account) {
    Observable<Void> removeAccountObservable = Observable.fromCallable(() -> {
      Realm defaultRealm = Realm.getDefaultInstance();
      defaultRealm.executeTransaction(realm -> {

        AccountRealm accountRealm = getAccountRealmAsBlocking(realm, account.getAccountId());
        if (accountRealm == null) {
          return;
        }

        accountRealm.removeFromRealm();
      });

      return null;
    });

    return removeAccountObservable.doOnNext(aVoid -> notifyAccountChanges());
  }

  @Override
  public Observable<List<Account>> getAccounts(Category category) {
    return changesPublishSubject.map(aVoid -> getAccountsForCategoryAsBlocking(category))
        .startWith(Observable.fromCallable(() -> getAccountsForCategoryAsBlocking(category)));
  }

  @Override
  public Observable<List<Account>> getAllAccounts() {
    return changesPublishSubject.map(aVoid -> getAccountsAsBlocking())
        .startWith(Observable.fromCallable(this::getAccountsAsBlocking));
  }

  private List<Account> getAccountsAsBlocking() {
    Realm realm = Realm.getDefaultInstance();
    realm.refresh();
    RealmResults<AccountRealm> realmResults =
        realm.where(AccountRealm.class).findAllSorted("order", Sort.ASCENDING);
    return accountRealmMapper.reverseTransform(realmResults);
  }

  private List<Account> getAccountsForCategoryAsBlocking(Category category) {
    Realm realm = Realm.getDefaultInstance();
    realm.refresh();

    RealmResults<AccountRealm> realmResults = realm.where(AccountRealm.class)
        .equalTo("category.categoryId", category.getCategoryId())
        .findAllSorted("order", Sort.ASCENDING);
    return accountRealmMapper.reverseTransform(realmResults);
  }

  private AccountRealm getAccountRealmAsBlocking(Realm realm, String accountId) {
    return realm.where(AccountRealm.class).equalTo("accountId", accountId).findFirst();
  }

  private CategoryRealm getCategoryAsBlocking(Realm realm, String categoryId) {
    return realm.where(CategoryRealm.class).equalTo("categoryId", categoryId).findFirst();
  }

  private void notifyAccountChanges() {
    changesPublishSubject.onNext(null);
  }
}
