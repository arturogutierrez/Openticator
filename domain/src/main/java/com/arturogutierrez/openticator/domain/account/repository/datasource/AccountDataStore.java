package com.arturogutierrez.openticator.domain.account.repository.datasource;

import com.arturogutierrez.openticator.domain.account.model.Account;
import java.util.List;
import rx.Observable;

public interface AccountDataStore {

  Observable<Account> add(Account account);

  Observable<List<Account>> getAccounts();
}
