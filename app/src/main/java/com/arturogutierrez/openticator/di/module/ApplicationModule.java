package com.arturogutierrez.openticator.di.module;

import android.content.Context;
import android.view.LayoutInflater;
import com.arturogutierrez.openticator.domain.account.repository.AccountRepository;
import com.arturogutierrez.openticator.domain.account.repository.AccountRepositoryImpl;
import com.arturogutierrez.openticator.executor.JobExecutor;
import com.arturogutierrez.openticator.executor.MainThread;
import com.arturogutierrez.openticator.executor.PostExecutionThread;
import com.arturogutierrez.openticator.executor.ThreadExecutor;
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

  @Singleton
  @Provides
  AccountRepository provideAccountRepository(AccountRepositoryImpl accountRepository) {
    return accountRepository;
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
}
