package com.arturogutierrez.openticator.domain.account.repository;

import com.arturogutierrez.openticator.domain.account.model.Account;
import com.arturogutierrez.openticator.domain.category.model.Category;
import java.util.List;
import rx.Observable;

public interface AccountDataStore {

  Observable<Account> add(Account account);

  Observable<Account> update(Account account);

  Observable<Void> remove(Account account);

  Observable<List<Account>> getAccounts(Category category);

  Observable<List<Account>> getAllAccounts();
}
