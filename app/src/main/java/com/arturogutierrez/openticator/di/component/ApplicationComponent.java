package com.arturogutierrez.openticator.di.component;

import android.content.Context;
import android.view.LayoutInflater;
import com.arturogutierrez.openticator.di.module.ApplicationModule;
import com.arturogutierrez.openticator.domain.executor.PostExecutionThread;
import com.arturogutierrez.openticator.domain.executor.ThreadExecutor;
import com.arturogutierrez.openticator.domain.repository.AccountRepository;
import com.arturogutierrez.openticator.view.activity.BaseActivity;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = { ApplicationModule.class })
public interface ApplicationComponent {

  void inject(BaseActivity baseActivity);

  Context context();

  LayoutInflater layoutInflater();

  AccountRepository provideAccountRepository();

  ThreadExecutor threadExecutor();

  PostExecutionThread postExecutionThread();
}
