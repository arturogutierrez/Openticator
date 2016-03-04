package com.arturogutierrez.openticator.di.module;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import com.arturogutierrez.openticator.domain.DatabaseConfigurator;
import com.arturogutierrez.openticator.domain.Preferences;
import com.arturogutierrez.openticator.domain.PreferencesImpl;
import com.arturogutierrez.openticator.domain.account.repository.AccountRepository;
import com.arturogutierrez.openticator.domain.account.repository.AccountRepositoryImpl;
import com.arturogutierrez.openticator.domain.category.CategorySelector;
import com.arturogutierrez.openticator.domain.category.repository.CategoryRepository;
import com.arturogutierrez.openticator.domain.category.repository.CategoryRepositoryImpl;
import com.arturogutierrez.openticator.domain.issuer.repository.IssuerRepository;
import com.arturogutierrez.openticator.domain.issuer.repository.IssuerRepositoryImpl;
import com.arturogutierrez.openticator.executor.JobExecutor;
import com.arturogutierrez.openticator.executor.MainThread;
import com.arturogutierrez.openticator.executor.PostExecutionThread;
import com.arturogutierrez.openticator.executor.ThreadExecutor;
import com.arturogutierrez.openticator.storage.RealmDatabaseConfigurator;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class ApplicationModule {

  private final Context context;

  public ApplicationModule(Context context) {
    this.context = context;
  }

  @Provides
  Context provideApplicationContext() {
    return context;
  }

  @Provides
  LayoutInflater provideLayoutInflater() {
    return LayoutInflater.from(context);
  }

  @Provides
  @Singleton
  ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
    return jobExecutor;
  }

  @Provides
  @Singleton
  PostExecutionThread providePostExecutionThread(MainThread mainThread) {
    return mainThread;
  }

  @Provides
  @Singleton
  SharedPreferences provideSharedPreferences(Context context) {
    return context.getSharedPreferences(Preferences.PREFERENCES_NAME, Context.MODE_PRIVATE);
  }

  @Provides
  @Singleton
  Preferences provideUserPreferences(PreferencesImpl preferences) {
    return preferences;
  }

  @Provides
  DatabaseConfigurator provideDatabaseConfigurator(RealmDatabaseConfigurator databaseConfigurator) {
    return databaseConfigurator;
  }

  @Singleton
  @Provides
  AccountRepository provideAccountRepository(AccountRepositoryImpl accountRepository) {
    return accountRepository;
  }

  @Singleton
  @Provides
  CategoryRepository provideCategoryRepository(CategoryRepositoryImpl categoryRepository) {
    return categoryRepository;
  }

  @Singleton
  @Provides
  IssuerRepository provideIssuerRepository(IssuerRepositoryImpl issuerRepository) {
    return issuerRepository;
  }

  @Singleton
  @Provides
  CategorySelector provideCategorySelector() {
    return new CategorySelector();
  }
}
