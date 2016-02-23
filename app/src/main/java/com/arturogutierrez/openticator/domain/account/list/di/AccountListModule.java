package com.arturogutierrez.openticator.domain.account.list.di;

import com.arturogutierrez.openticator.di.PerActivity;
import com.arturogutierrez.openticator.domain.account.AccountFactory;
import com.arturogutierrez.openticator.domain.account.interactor.DeleteAccountsInteractor;
import com.arturogutierrez.openticator.domain.account.interactor.GetAccountPasscodesInteractor;
import com.arturogutierrez.openticator.domain.account.interactor.GetAccountsInteractor;
import com.arturogutierrez.openticator.domain.account.interactor.UpdateAccountInteractor;
import com.arturogutierrez.openticator.domain.account.repository.AccountRepository;
import com.arturogutierrez.openticator.domain.otp.OneTimePasswordFactory;
import com.arturogutierrez.openticator.executor.PostExecutionThread;
import com.arturogutierrez.openticator.executor.ThreadExecutor;
import dagger.Module;
import dagger.Provides;

@Module
public class AccountListModule {

  public AccountListModule() {

  }

  @Provides
  @PerActivity
  GetAccountsInteractor provideGetAccountsInteractor(AccountRepository accountRepository,
      ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
    return new GetAccountsInteractor(accountRepository, threadExecutor, postExecutionThread);
  }

  @Provides
  @PerActivity
  GetAccountPasscodesInteractor provideGetAccountPasscodesInteractor(
      AccountRepository accountRepository, OneTimePasswordFactory oneTimePasswordFactory,
      ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
    return new GetAccountPasscodesInteractor(accountRepository, oneTimePasswordFactory,
        threadExecutor, postExecutionThread);
  }

  @Provides
  @PerActivity
  DeleteAccountsInteractor provideDeleteAccountsInteractor(AccountRepository accountRepository,
      ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
    return new DeleteAccountsInteractor(accountRepository, threadExecutor, postExecutionThread);
  }

  @Provides
  @PerActivity
  UpdateAccountInteractor provideUpdateAccountInteractor(AccountRepository accountRepository,
      AccountFactory accountFactory, ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    return new UpdateAccountInteractor(accountRepository, accountFactory, threadExecutor,
        postExecutionThread);
  }
}
