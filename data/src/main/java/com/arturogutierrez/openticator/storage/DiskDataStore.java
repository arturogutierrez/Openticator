package com.arturogutierrez.openticator.storage;

import android.content.Context;
import com.arturogutierrez.openticator.domain.account.model.Account;
import com.arturogutierrez.openticator.domain.account.repository.datasource.AccountDataStore;
import com.arturogutierrez.openticator.storage.realm.helpers.RealmObservable;
import com.arturogutierrez.openticator.storage.realm.mapper.AccountRealmMapper;
import com.arturogutierrez.openticator.storage.realm.model.AccountRealm;
import io.realm.Realm;
import java.util.List;
import javax.inject.Inject;
import rx.Observable;

public class DiskDataStore implements AccountDataStore {

  private final Context context;
  private final AccountRealmMapper accountRealmMapper;

  @Inject
  public DiskDataStore(Context context, AccountRealmMapper accountRealmMapper) {
    this.context = context;
    this.accountRealmMapper = accountRealmMapper;
  }

  @Override
  public Observable<Account> add(Account account) {
    return getAccounts().flatMap(accounts -> {
      int numberOfAccounts = accounts.size();

      return Observable.create(subscriber -> {
        AccountRealm accountRealm = accountRealmMapper.transform(account);
        accountRealm.setOrder(numberOfAccounts);

        Realm defaultRealm = Realm.getDefaultInstance();
        defaultRealm.executeTransaction(realm -> {
          realm.copyToRealm(accountRealm);
        });

        subscriber.onNext(account);
        subscriber.onCompleted();
      });
    });
  }

  @Override
  public Observable<List<Account>> getAccounts() {
    return RealmObservable.results(context, realm -> realm.where(AccountRealm.class).findAll())
        .map(accountRealmMapper::transform);
  }
}
