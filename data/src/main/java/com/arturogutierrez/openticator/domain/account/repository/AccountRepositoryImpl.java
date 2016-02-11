package com.arturogutierrez.openticator.domain.account.repository;

import com.arturogutierrez.openticator.domain.account.model.Account;
import com.arturogutierrez.openticator.domain.account.repository.datasource.AccountDataStore;
import com.arturogutierrez.openticator.storage.DiskDataStore;
import java.util.List;
import javax.inject.Inject;
import rx.Observable;

public class AccountRepositoryImpl implements AccountRepository {

  private final AccountDataStore accountDataStore;

  @Inject
  public AccountRepositoryImpl(DiskDataStore diskDataStore) {
    this.accountDataStore = diskDataStore;
  }

  @Override
  public Observable<Account> add(Account account) {
    return accountDataStore.add(account);
  }

  @Override
  public Observable<List<Account>> getAccounts() {
    throw new UnsupportedOperationException("Not implemented yet");
  }
}
