package com.arturogutierrez.openticator.data.storage;

import com.arturogutierrez.openticator.data.storage.realm.mapper.AccountRealmMapper;
import com.arturogutierrez.openticator.data.storage.realm.model.AccountRealm;
import com.arturogutierrez.openticator.domain.model.Account;
import com.arturogutierrez.openticator.domain.repository.datasource.AccountDataStore;
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
