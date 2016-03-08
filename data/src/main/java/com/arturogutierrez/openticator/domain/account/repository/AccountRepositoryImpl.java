package com.arturogutierrez.openticator.domain.account.repository;

import com.arturogutierrez.openticator.domain.account.model.Account;
import com.arturogutierrez.openticator.domain.category.model.Category;
import com.arturogutierrez.openticator.domain.sync.SyncTimestamp;
import com.arturogutierrez.openticator.storage.AccountDiskDataStore;
import java.util.List;
import javax.inject.Inject;
import rx.Observable;

public class AccountRepositoryImpl implements AccountRepository {

  private final AccountDataStore accountDataStore;
  private final SyncTimestamp syncTimestamp;

  @Inject
  public AccountRepositoryImpl(AccountDiskDataStore accountDiskDataStore,
      SyncTimestamp syncTimestamp) {
    this.accountDataStore = accountDiskDataStore;
    this.syncTimestamp = syncTimestamp;
  }

  @Override
  public Observable<Account> add(Account account) {
    return accountDataStore.add(account)
        .doOnNext(newAccount -> syncTimestamp.updateLocalModifiedTimestamp());
  }

  @Override
  public Observable<Account> update(Account account) {
    return accountDataStore.update(account)
        .doOnNext(newAccount -> syncTimestamp.updateLocalModifiedTimestamp());
  }

  @Override
  public Observable<Void> remove(Account account) {
    return accountDataStore.remove(account)
        .doOnNext(newAccount -> syncTimestamp.updateLocalModifiedTimestamp());
  }

  @Override
  public Observable<List<Account>> getAccounts(Category category) {
    return accountDataStore.getAccounts(category);
  }

  @Override
  public Observable<List<Account>> getAllAccounts() {
    return accountDataStore.getAllAccounts();
  }
}
