package com.arturogutierrez.openticator.domain.repository;

import com.arturogutierrez.openticator.domain.model.Account;
import java.util.List;
import rx.Observable;

public interface AccountRepository {

  Observable<Account> add(Account account);

  Observable<List<Account>> getAccounts();
}
