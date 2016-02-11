package com.arturogutierrez.openticator.domain.account.list.di;

import com.arturogutierrez.openticator.di.PerActivity;
import com.arturogutierrez.openticator.domain.account.interactor.GetAccounts;
import dagger.Module;
import dagger.Provides;

@Module
public class AccountListModule {

  public AccountListModule() {

  }

  @Provides
  @PerActivity
  GetAccounts provideGetAccountsInteractor(GetAccounts getAccountsInteractor) {
    return getAccountsInteractor;
  }
}
