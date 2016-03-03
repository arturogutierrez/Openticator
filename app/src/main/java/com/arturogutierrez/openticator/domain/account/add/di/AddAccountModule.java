package com.arturogutierrez.openticator.domain.account.add.di;

import com.arturogutierrez.openticator.di.PerActivity;
import com.arturogutierrez.openticator.domain.account.AccountFactory;
import com.arturogutierrez.openticator.domain.account.interactor.AddAccountInteractor;
import com.arturogutierrez.openticator.domain.account.repository.AccountRepository;
import com.arturogutierrez.openticator.domain.category.CategoryFactory;
import com.arturogutierrez.openticator.domain.category.CategorySelector;
import com.arturogutierrez.openticator.domain.category.repository.CategoryRepository;
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
      AccountFactory accountFactory, CategoryRepository categoryRepository,
      CategorySelector categorySelector, CategoryFactory categoryFactory,
      ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
    return new AddAccountInteractor(accountRepository, accountFactory, categoryRepository,
        categorySelector, categoryFactory, threadExecutor, postExecutionThread);
  }
}
