package com.arturogutierrez.openticator.domain.backup;

import com.arturogutierrez.openticator.domain.account.model.Account;
import com.arturogutierrez.openticator.storage.export.mapper.AccountEntityMapper;
import com.arturogutierrez.openticator.storage.export.model.AccountEntity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Arrays;
import java.util.List;
import rx.Observable;

public class AccountsSerializatorImpl implements AccountsSerializator {

  private final AccountEntityMapper accountEntityMapper;
  private final Gson gson;

  public AccountsSerializatorImpl(AccountEntityMapper accountEntityMapper) {
    this.accountEntityMapper = accountEntityMapper;
    this.gson = new GsonBuilder().create();
  }

  @Override
  public Observable<String> serializeAccounts(List<Account> accounts) {
    return Observable.from(accounts)
        .map(accountEntityMapper::transform)
        .toList()
        .map(this::convertToJSON);
  }

  @Override
  public Observable<List<Account>> deserializeJSON(String JSON) {
    return Observable.fromCallable(() -> convertFromJSON(JSON))
        .flatMap(Observable::from)
        .map(accountEntityMapper::reverseTransform)
        .map(Arrays::asList);
  }

  private String convertToJSON(List<AccountEntity> accounts) {
    return gson.toJson(accounts);
  }

  private AccountEntity[] convertFromJSON(String JSON) {
    return gson.fromJson(JSON, AccountEntity[].class);
  }
}
