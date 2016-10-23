package com.arturogutierrez.openticator.di.module

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import com.arturogutierrez.openticator.storage.database.DatabaseConfigurator
import com.arturogutierrez.openticator.storage.preferences.Preferences
import com.arturogutierrez.openticator.storage.preferences.PreferencesImpl
import com.arturogutierrez.openticator.domain.account.repository.AccountRepository
import com.arturogutierrez.openticator.domain.account.repository.AccountRepositoryImpl
import com.arturogutierrez.openticator.domain.category.CategorySelector
import com.arturogutierrez.openticator.domain.category.repository.CategoryRepository
import com.arturogutierrez.openticator.domain.category.repository.CategoryRepositoryImpl
import com.arturogutierrez.openticator.domain.issuer.repository.IssuerRepository
import com.arturogutierrez.openticator.domain.issuer.repository.IssuerRepositoryImpl
import com.arturogutierrez.openticator.executor.JobExecutor
import com.arturogutierrez.openticator.executor.MainThread
import com.arturogutierrez.openticator.executor.PostExecutionThread
import com.arturogutierrez.openticator.executor.ThreadExecutor
import com.arturogutierrez.openticator.storage.ClipboardRepositoryImpl
import com.arturogutierrez.openticator.storage.clipboard.ClipboardRepository
import com.arturogutierrez.openticator.storage.realm.AccountDiskDataStore
import com.arturogutierrez.openticator.storage.realm.CategoryDiskDataStore
import com.arturogutierrez.openticator.storage.realm.RealmDatabaseConfigurator
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(val context: Context) {

  @Provides
  internal fun provideApplicationContext(): Context {
    return context
  }

  @Provides
  internal fun provideLayoutInflater(): LayoutInflater {
    return LayoutInflater.from(context)
  }

  @Provides
  @Singleton
  internal fun provideThreadExecutor(jobExecutor: JobExecutor): ThreadExecutor {
    return jobExecutor
  }

  @Provides
  @Singleton
  internal fun providePostExecutionThread(mainThread: MainThread): PostExecutionThread {
    return mainThread
  }

  @Provides
  @Singleton
  internal fun provideSharedPreferences(context: Context): SharedPreferences {
    return context.getSharedPreferences(Preferences.preferencesName, Context.MODE_PRIVATE)
  }

  @Provides
  @Singleton
  internal fun provideUserPreferences(preferences: PreferencesImpl): Preferences {
    return preferences
  }

  @Provides
  internal fun provideDatabaseConfigurator(databaseConfigurator: RealmDatabaseConfigurator): DatabaseConfigurator {
    return databaseConfigurator
  }

  @Singleton
  @Provides
  internal fun provideAccountRepository(accountDiskDataStore: AccountDiskDataStore): AccountRepository {
    return AccountRepositoryImpl(accountDiskDataStore)
  }

  @Singleton
  @Provides
  internal fun provideCategoryRepository(categoryDiskDataStore: CategoryDiskDataStore): CategoryRepository {
    return CategoryRepositoryImpl(categoryDiskDataStore)
  }

  @Singleton
  @Provides
  internal fun provideIssuerRepository(issuerRepository: IssuerRepositoryImpl): IssuerRepository {
    return issuerRepository
  }

  @Singleton
  @Provides
  internal fun provideCategorySelector(): CategorySelector {
    return CategorySelector()
  }

  @Provides
  internal fun provideClipboardRepository(clipboardRepository: ClipboardRepositoryImpl): ClipboardRepository {
    return clipboardRepository
  }
}
