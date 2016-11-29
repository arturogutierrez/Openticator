package com.arturogutierrez.openticator.di.module

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
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
import com.arturogutierrez.openticator.storage.database.DatabaseConfigurator
import com.arturogutierrez.openticator.storage.preferences.Preferences
import com.arturogutierrez.openticator.storage.preferences.PreferencesImpl
import com.arturogutierrez.openticator.storage.realm.AccountDiskDataStore
import com.arturogutierrez.openticator.storage.realm.CategoryDiskDataStore
import com.arturogutierrez.openticator.storage.realm.RealmDatabaseConfigurator
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(val context: Context) {

  @Provides
  fun provideApplicationContext() = context

  @Provides
  fun provideLayoutInflater(): LayoutInflater = LayoutInflater.from(context)

  @Provides
  @Singleton
  fun provideThreadExecutor(jobExecutor: JobExecutor): ThreadExecutor = jobExecutor

  @Provides
  @Singleton
  fun providePostExecutionThread(mainThread: MainThread): PostExecutionThread = mainThread

  @Provides
  @Singleton
  fun provideSharedPreferences(context: Context): SharedPreferences = context.getSharedPreferences(Preferences.preferencesName, Context.MODE_PRIVATE)

  @Provides
  @Singleton
  fun provideUserPreferences(preferencesImpl: PreferencesImpl): Preferences = preferencesImpl

  @Provides
  fun provideDatabaseConfigurator(databaseConfigurator: RealmDatabaseConfigurator): DatabaseConfigurator = databaseConfigurator

  @Singleton
  @Provides
  fun provideAccountRepository(accountDiskDataStore: AccountDiskDataStore): AccountRepository = AccountRepositoryImpl(accountDiskDataStore)

  @Singleton
  @Provides
  fun provideCategoryRepository(categoryDiskDataStore: CategoryDiskDataStore): CategoryRepository = CategoryRepositoryImpl(categoryDiskDataStore)

  @Singleton
  @Provides
  fun provideIssuerRepository(issuerRepository: IssuerRepositoryImpl): IssuerRepository = issuerRepository

  @Singleton
  @Provides
  fun provideCategorySelector() = CategorySelector()

  @Provides
  fun provideClipboardRepository(clipboardRepository: ClipboardRepositoryImpl): ClipboardRepository = clipboardRepository
}
