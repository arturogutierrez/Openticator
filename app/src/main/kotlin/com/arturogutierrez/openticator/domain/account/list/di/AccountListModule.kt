package com.arturogutierrez.openticator.domain.account.list.di

import com.arturogutierrez.openticator.domain.account.interactor.CopyToClipboardInteractor
import com.arturogutierrez.openticator.domain.account.interactor.DeleteAccountsInteractor
import com.arturogutierrez.openticator.domain.account.interactor.GetAccountPasscodesInteractor
import com.arturogutierrez.openticator.domain.account.interactor.GetAccountsInteractor
import com.arturogutierrez.openticator.domain.account.interactor.UpdateAccountInteractor
import com.arturogutierrez.openticator.domain.account.repository.AccountRepository
import com.arturogutierrez.openticator.domain.category.CategoryFactory
import com.arturogutierrez.openticator.domain.category.CategorySelector
import com.arturogutierrez.openticator.domain.category.interactor.AddAccountToCategoryInteractor
import com.arturogutierrez.openticator.domain.category.interactor.AddCategoryInteractor
import com.arturogutierrez.openticator.domain.category.interactor.GetCategoriesInteractor
import com.arturogutierrez.openticator.domain.category.repository.CategoryRepository
import com.arturogutierrez.openticator.domain.issuer.interactor.GetIssuersInteractor
import com.arturogutierrez.openticator.domain.issuer.repository.IssuerRepository
import com.arturogutierrez.openticator.domain.otp.OneTimePasswordFactory
import com.arturogutierrez.openticator.domain.otp.time.CurrentTimeProvider
import com.arturogutierrez.openticator.domain.otp.time.TimeProvider
import com.arturogutierrez.openticator.executor.PostExecutionThread
import com.arturogutierrez.openticator.executor.ThreadExecutor
import com.arturogutierrez.openticator.storage.clipboard.ClipboardRepository
import dagger.Module
import dagger.Provides

@Module
class AccountListModule {

  @Provides
  fun provideGetAccountsInteractor(accountRepository: AccountRepository,
                                   threadExecutor: ThreadExecutor, postExecutionThread: PostExecutionThread): GetAccountsInteractor {
    return GetAccountsInteractor(accountRepository, threadExecutor, postExecutionThread)
  }

  @Provides
  fun provideGetAccountPasscodesInteractor(
      categorySelector: CategorySelector, categoryFactory: CategoryFactory,
      accountRepository: AccountRepository, oneTimePasswordFactory: OneTimePasswordFactory,
      threadExecutor: ThreadExecutor, postExecutionThread: PostExecutionThread): GetAccountPasscodesInteractor {
    return GetAccountPasscodesInteractor(categorySelector, categoryFactory, accountRepository,
        oneTimePasswordFactory, threadExecutor, postExecutionThread)
  }

  @Provides
  fun provideDeleteAccountsInteractor(accountRepository: AccountRepository,
                                      threadExecutor: ThreadExecutor, postExecutionThread: PostExecutionThread): DeleteAccountsInteractor {
    return DeleteAccountsInteractor(accountRepository, threadExecutor, postExecutionThread)
  }

  @Provides
  fun provideUpdateAccountInteractor(accountRepository: AccountRepository,
                                     threadExecutor: ThreadExecutor,
                                     postExecutionThread: PostExecutionThread): UpdateAccountInteractor {
    return UpdateAccountInteractor(accountRepository, threadExecutor, postExecutionThread)
  }

  @Provides
  fun provideGetCategoriesInteractor(categoryRepository: CategoryRepository,
                                     threadExecutor: ThreadExecutor, postExecutionThread: PostExecutionThread): GetCategoriesInteractor {
    return GetCategoriesInteractor(categoryRepository, threadExecutor, postExecutionThread)
  }

  @Provides
  fun provideAddCategoryInteractor(categoryRepository: CategoryRepository,
                                   categoryFactory: CategoryFactory, threadExecutor: ThreadExecutor,
                                   postExecutionThread: PostExecutionThread): AddCategoryInteractor {
    return AddCategoryInteractor(categoryRepository, categoryFactory, threadExecutor,
        postExecutionThread)
  }

  @Provides
  fun provideAddAccountToCategoryInteractor(
      categoryRepository: CategoryRepository, threadExecutor: ThreadExecutor,
      postExecutionThread: PostExecutionThread): AddAccountToCategoryInteractor {
    return AddAccountToCategoryInteractor(categoryRepository, threadExecutor,
        postExecutionThread)
  }

  @Provides
  fun provideGetIssuerInteractor(issuerRepository: IssuerRepository,
                                 threadExecutor: ThreadExecutor, postExecutionThread: PostExecutionThread): GetIssuersInteractor {
    return GetIssuersInteractor(issuerRepository, threadExecutor, postExecutionThread)
  }

  @Provides
  fun provideTimeProvider(): TimeProvider {
    return CurrentTimeProvider()
  }

  @Provides
  fun provideCopyToClipboardInteractor(clipboardRepository: ClipboardRepository,
                                       threadExecutor: ThreadExecutor,
                                       postExecutionThread: PostExecutionThread): CopyToClipboardInteractor {
    return CopyToClipboardInteractor(clipboardRepository, threadExecutor, postExecutionThread)
  }
}
