package com.arturogutierrez.openticator.domain.account.add.di;

import com.arturogutierrez.openticator.di.PerActivity;
import com.arturogutierrez.openticator.domain.account.AccountFactory;
import com.arturogutierrez.openticator.domain.account.interactor.AddAccountInteractor;
import com.arturogutierrez.openticator.domain.account.repository.AccountRepository;
import com.arturogutierrez.openticator.executor.PostExecutionThread;
import com.arturogutierrez.openticator.executor.ThreadExecutor;
import dagger.Module;
import dagger.Provides;

@Module
public class AddAccountModule {

  public AddAccountModule() {

  }

  @Provides
  @PerActivity
  AddAccountInteractor provideAddAccountInteractor(AccountRepository accountRepository,
      AccountFactory accountFactory, ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    return new AddAccountInteractor(accountRepository, accountFactory, threadExecutor,
        postExecutionThread);
  }
}
