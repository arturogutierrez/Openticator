package com.arturogutierrez.openticator.domain.account.add.di

import com.arturogutierrez.openticator.di.PerActivity
import com.arturogutierrez.openticator.domain.account.AccountFactory
import com.arturogutierrez.openticator.domain.account.interactor.AddAccountInteractor
import com.arturogutierrez.openticator.domain.account.repository.AccountRepository
import com.arturogutierrez.openticator.domain.category.CategoryFactory
import com.arturogutierrez.openticator.domain.category.CategorySelector
import com.arturogutierrez.openticator.domain.category.repository.CategoryRepository
import com.arturogutierrez.openticator.executor.PostExecutionThread
import com.arturogutierrez.openticator.executor.ThreadExecutor
import dagger.Module
import dagger.Provides

@Module
class AddAccountModule {

  @Provides
  @PerActivity
  internal fun provideAddAccountInteractor(accountRepository: AccountRepository,
                                           accountFactory: AccountFactory, categoryRepository: CategoryRepository,
                                           categorySelector: CategorySelector, categoryFactory: CategoryFactory,
                                           threadExecutor: ThreadExecutor, postExecutionThread: PostExecutionThread): AddAccountInteractor {
    return AddAccountInteractor(accountRepository, accountFactory, categoryRepository,
        categorySelector, categoryFactory, threadExecutor, postExecutionThread)
  }
}
