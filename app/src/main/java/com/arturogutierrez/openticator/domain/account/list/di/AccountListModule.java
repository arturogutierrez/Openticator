package com.arturogutierrez.openticator.domain.account.list.di;

import com.arturogutierrez.openticator.di.PerActivity;
import com.arturogutierrez.openticator.domain.account.AccountFactory;
import com.arturogutierrez.openticator.domain.account.interactor.DeleteAccountsInteractor;
import com.arturogutierrez.openticator.domain.account.interactor.GetAccountPasscodesInteractor;
import com.arturogutierrez.openticator.domain.account.interactor.GetAccountsInteractor;
import com.arturogutierrez.openticator.domain.account.interactor.UpdateAccountInteractor;
import com.arturogutierrez.openticator.domain.account.repository.AccountRepository;
import com.arturogutierrez.openticator.domain.category.CategoryFactory;
import com.arturogutierrez.openticator.domain.category.CategorySelector;
import com.arturogutierrez.openticator.domain.category.interactor.AddAccountToCategoryInteractor;
import com.arturogutierrez.openticator.domain.category.interactor.AddCategoryInteractor;
import com.arturogutierrez.openticator.domain.category.interactor.GetCategoriesInteractor;
import com.arturogutierrez.openticator.domain.category.repository.CategoryRepository;
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
      CategorySelector categorySelector, CategoryFactory categoryFactory,
      AccountRepository accountRepository, OneTimePasswordFactory oneTimePasswordFactory,
      ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
    return new GetAccountPasscodesInteractor(categorySelector, categoryFactory, accountRepository,
        oneTimePasswordFactory, threadExecutor, postExecutionThread);
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

  @Provides
  @PerActivity
  GetCategoriesInteractor provideGetCategoriesInteractor(CategoryRepository categoryRepository,
      ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
    return new GetCategoriesInteractor(categoryRepository, threadExecutor, postExecutionThread);
  }

  @Provides
  @PerActivity
  AddCategoryInteractor provideAddCategoryInteractor(CategoryRepository categoryRepository,
      CategoryFactory categoryFactory, ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    return new AddCategoryInteractor(categoryRepository, categoryFactory, threadExecutor,
        postExecutionThread);
  }

  @Provides
  @PerActivity
  AddAccountToCategoryInteractor providesAddAccountToCategoryInteractor(
      CategoryRepository categoryRepository, ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    return new AddAccountToCategoryInteractor(categoryRepository, threadExecutor,
        postExecutionThread);
  }
}
