package com.arturogutierrez.openticator.domain.account.interactor;

import com.arturogutierrez.openticator.interactor.Interactor;
import com.arturogutierrez.openticator.executor.PostExecutionThread;
import com.arturogutierrez.openticator.executor.ThreadExecutor;
import com.arturogutierrez.openticator.domain.account.model.Account;
import com.arturogutierrez.openticator.domain.account.repository.AccountRepository;
import java.util.List;
import javax.inject.Inject;
import rx.Observable;

public class GetAccounts extends Interactor<List<Account>> {

  private final AccountRepository accountRepository;

  @Inject
  public GetAccounts(AccountRepository accountRepository, ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.accountRepository = accountRepository;
  }

  @Override
  public Observable<List<Account>> createObservable() {
    return accountRepository.getAccounts();
  }
}
