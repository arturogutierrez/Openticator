package com.arturogutierrez.openticator.domain.account.interactor;

import com.arturogutierrez.openticator.domain.account.model.Account;
import com.arturogutierrez.openticator.domain.account.repository.AccountRepository;
import com.arturogutierrez.openticator.executor.PostExecutionThread;
import com.arturogutierrez.openticator.executor.ThreadExecutor;
import com.arturogutierrez.openticator.interactor.Interactor;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import rx.Observable;

public class DeleteAccountsInteractor extends Interactor<Void> {

  private final AccountRepository accountRepository;
  private Set<Account> accounts;

  public DeleteAccountsInteractor(AccountRepository accountRepository,
      ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.accountRepository = accountRepository;
  }

  public void configure(Set<Account> accounts) {
    this.accounts = accounts;
  }

  @Override
  public Observable<Void> createObservable() {
    if (accounts == null || accounts.size() == 0) {
      throw new IllegalStateException("You must call configure before execute the interactor");
    }

    List<Observable<Void>> removeAccountObservables = new ArrayList<>(accounts.size());
    for (Account account : accounts) {
      removeAccountObservables.add(accountRepository.remove(account));
    }

    return Observable.merge(removeAccountObservables);
  }
}
