package com.arturogutierrez.openticator.domain.account.interactor;

import com.arturogutierrez.openticator.domain.account.AccountFactory;
import com.arturogutierrez.openticator.domain.account.model.Account;
import com.arturogutierrez.openticator.domain.account.repository.AccountRepository;
import com.arturogutierrez.openticator.executor.PostExecutionThread;
import com.arturogutierrez.openticator.executor.ThreadExecutor;
import com.arturogutierrez.openticator.interactor.Interactor;
import rx.Observable;

public class AddAccountInteractor extends Interactor<Account> {

  private final AccountRepository accountRepository;

  private Account newAccount;

  public AddAccountInteractor(AccountRepository accountRepository, ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.accountRepository = accountRepository;
  }

  public void configure(String accountName, String accountSecret) {
    newAccount = AccountFactory.createAccount(accountName, accountSecret);
  }

  @Override
  public Observable<Account> createObservable() {
    if (newAccount == null) {
      throw new IllegalStateException("You must call configure before execute the interactor");
    }

    return accountRepository.add(newAccount);
  }
}
