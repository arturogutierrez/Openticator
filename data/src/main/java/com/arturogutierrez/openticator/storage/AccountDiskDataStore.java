package com.arturogutierrez.openticator.storage;

import com.arturogutierrez.openticator.domain.account.model.Account;
import com.arturogutierrez.openticator.domain.account.repository.AccountDataStore;
import com.arturogutierrez.openticator.storage.realm.mapper.AccountRealmMapper;
import com.arturogutierrez.openticator.storage.realm.model.AccountRealm;
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
    Observable<Account> accountObservable = getAccounts().flatMap(accounts -> {
      int numberOfAccounts = accounts.size();

      return Observable.create(subscriber -> {
        AccountRealm accountRealm = accountRealmMapper.transform(account);
        accountRealm.setOrder(numberOfAccounts);

        Realm defaultRealm = Realm.getDefaultInstance();
        defaultRealm.executeTransaction(realm -> realm.copyToRealm(accountRealm));

        subscriber.onNext(account);
        subscriber.onCompleted();
      });
    });

    return accountObservable.doOnNext(accountAdded -> notifyAccountChanges());
  }

  @Override
  public Observable<Account> update(Account account) {
    Observable<Account> updateAccountObservable = Observable.create(subscriber -> {
      Realm defaultRealm = Realm.getDefaultInstance();
      defaultRealm.executeTransaction(realm -> {
        AccountRealm accountRealm = getAccountRealmAsBlocking(realm, account.getAccountId());
        if (accountRealm == null) {
          return;
        }

        accountRealmMapper.copyToAccountRealm(accountRealm, account);
      });

      subscriber.onNext(account);
      subscriber.onCompleted();
    });

    return updateAccountObservable.doOnNext(aVoid -> notifyAccountChanges());
  }

  @Override
  public Observable<Void> remove(Account account) {
    Observable<Void> removeAccountObservable = Observable.create(subscriber -> {
      Realm defaultRealm = Realm.getDefaultInstance();
      defaultRealm.executeTransaction(realm -> {

        AccountRealm accountRealm = getAccountRealmAsBlocking(realm, account.getAccountId());
        if (accountRealm == null) {
          return;
        }

        accountRealm.removeFromRealm();
      });

      subscriber.onNext(null);
      subscriber.onCompleted();
    });

    return removeAccountObservable.doOnNext(aVoid -> notifyAccountChanges());
  }

  @Override
  public Observable<List<Account>> getAccounts() {
    return changesPublishSubject.map(aVoid1 -> getAccountsAsBlocking())
        .startWith(Observable.create(subscriber -> {
          subscriber.onNext(getAccountsAsBlocking());
          subscriber.onCompleted();
        }));
  }

  private List<Account> getAccountsAsBlocking() {
    Realm realm = Realm.getDefaultInstance();
    realm.refresh();
    RealmResults<AccountRealm> realmResults =
        realm.where(AccountRealm.class).findAllSorted("order", Sort.ASCENDING);
    return accountRealmMapper.reverseTransform(realmResults);
  }

  private AccountRealm getAccountRealmAsBlocking(Realm realm, String accountId) {
    return realm.where(AccountRealm.class).equalTo("accountId", accountId).findFirst();
  }

  private void notifyAccountChanges() {
    changesPublishSubject.onNext(null);
  }
}
