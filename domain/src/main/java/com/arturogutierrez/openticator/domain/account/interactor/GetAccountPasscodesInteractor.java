package com.arturogutierrez.openticator.domain.account.interactor;

import com.arturogutierrez.openticator.domain.account.model.Account;
import com.arturogutierrez.openticator.domain.account.model.AccountPasscode;
import com.arturogutierrez.openticator.domain.account.repository.AccountRepository;
import com.arturogutierrez.openticator.domain.otp.OneTimePassword;
import com.arturogutierrez.openticator.domain.otp.OneTimePasswordFactory;
import com.arturogutierrez.openticator.domain.otp.model.Passcode;
import com.arturogutierrez.openticator.executor.PostExecutionThread;
import com.arturogutierrez.openticator.executor.ThreadExecutor;
import com.arturogutierrez.openticator.interactor.Interactor;
import java.util.List;
import rx.Observable;

public class GetAccountPasscodesInteractor extends Interactor<List<AccountPasscode>> {

  private final AccountRepository accountRepository;
  private final OneTimePasswordFactory oneTimePasswordFactory;

  public GetAccountPasscodesInteractor(AccountRepository accountRepository,
      OneTimePasswordFactory oneTimePasswordFactory, ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.accountRepository = accountRepository;
    this.oneTimePasswordFactory = oneTimePasswordFactory;
  }

  @Override
  public Observable<List<AccountPasscode>> createObservable() {
    return accountRepository.getAccounts()
        .flatMap(Observable::from)
        .map(this::calculatePasscode)
        .toList();
  }

  private AccountPasscode calculatePasscode(Account account) {
    // TODO: Pick right delta offset time
    OneTimePassword oneTimePassword = oneTimePasswordFactory.createOneTimePassword(account, 0);
    Passcode passcode = oneTimePassword.generate();
    return new AccountPasscode(account.getName(), account.getIssuer(), passcode);
  }
}
