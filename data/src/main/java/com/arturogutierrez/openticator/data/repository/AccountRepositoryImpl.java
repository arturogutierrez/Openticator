package com.arturogutierrez.openticator.data.repository;

import com.arturogutierrez.openticator.domain.model.Account;
import com.arturogutierrez.openticator.domain.repository.AccountRepository;
import java.util.List;
import javax.inject.Inject;
import rx.Observable;

public class AccountRepositoryImpl implements AccountRepository {

  @Inject
  public AccountRepositoryImpl() {

  }

  @Override
  public Observable<Account> add(Account account) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public Observable<List<Account>> getAccounts() {
    throw new UnsupportedOperationException("Not implemented yet");
  }
}
