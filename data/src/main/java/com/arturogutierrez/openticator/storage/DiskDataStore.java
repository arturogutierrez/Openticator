package com.arturogutierrez.openticator.storage;

import com.arturogutierrez.openticator.storage.realm.mapper.AccountRealmMapper;
import com.arturogutierrez.openticator.storage.realm.model.AccountRealm;
import com.arturogutierrez.openticator.domain.account.model.Account;
import com.arturogutierrez.openticator.domain.account.repository.datasource.AccountDataStore;
import io.realm.Realm;
import java.util.List;
import rx.Observable;

public class DiskDataStore implements AccountDataStore {

  private final AccountRealmMapper accountRealmMapper;

  public DiskDataStore(AccountRealmMapper accountRealmMapper) {
    this.accountRealmMapper = accountRealmMapper;
  }

  @Override
  public Observable<Account> add(Account account) {
    return Observable.create(subscriber -> {
      AccountRealm accountRealm = accountRealmMapper.transform(account);

      Realm defaultRealm = Realm.getDefaultInstance();
      defaultRealm.executeTransaction(realm -> {
        realm.copyToRealm(accountRealm);
      });

      subscriber.onNext(account);
      subscriber.onCompleted();
    });
  }

  @Override
  public Observable<List<Account>> getAccounts() {
    Realm realm = Realm.getDefaultInstance();

    return realm.where(AccountRealm.class)
        .findAllAsync()
        .asObservable()
        .map(accountRealmMapper::transform);
  }
}
