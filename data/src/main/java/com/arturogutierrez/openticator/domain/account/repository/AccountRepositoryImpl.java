package com.arturogutierrez.openticator.domain.account.repository;

import com.arturogutierrez.openticator.domain.account.model.Account;
import com.arturogutierrez.openticator.domain.category.model.Category;
import com.arturogutierrez.openticator.storage.AccountDiskDataStore;
import java.util.List;
import javax.inject.Inject;
import rx.Observable;

public class AccountRepositoryImpl implements AccountRepository {

  private final AccountDataStore accountDataStore;

  @Inject
  public AccountRepositoryImpl(AccountDiskDataStore accountDiskDataStore) {
    this.accountDataStore = accountDiskDataStore;
  }

  @Override
  public Observable<Account> add(Account account) {
    return accountDataStore.add(account);
  }

  @Override
  public Observable<Account> update(Account account) {
    return accountDataStore.update(account);
  }

  @Override
  public Observable<Void> remove(Account account) {
    return accountDataStore.remove(account);
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
