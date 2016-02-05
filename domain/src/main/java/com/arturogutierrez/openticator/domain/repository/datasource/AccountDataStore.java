package com.arturogutierrez.openticator.domain.repository.datasource;

import com.arturogutierrez.openticator.domain.model.Account;
import java.util.List;
import rx.Observable;

public interface AccountDataStore {

  Observable<Account> add(Account account);

  Observable<List<Account>> getAccounts();
}
