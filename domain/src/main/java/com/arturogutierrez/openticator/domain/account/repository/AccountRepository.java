package com.arturogutierrez.openticator.domain.account.repository;

import com.arturogutierrez.openticator.domain.account.model.Account;
import java.util.List;
import rx.Observable;

public interface AccountRepository {

  Observable<Account> add(Account account);

  Observable<Account> update(Account account);

  Observable<Void> remove(Account account);

  Observable<List<Account>> getAccounts();
}
