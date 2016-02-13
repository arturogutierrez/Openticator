package com.arturogutierrez.openticator.di.component;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import com.arturogutierrez.openticator.di.module.ApplicationModule;
import com.arturogutierrez.openticator.domain.Preferences;
import com.arturogutierrez.openticator.domain.account.repository.AccountRepository;
import com.arturogutierrez.openticator.executor.PostExecutionThread;
import com.arturogutierrez.openticator.executor.ThreadExecutor;
import com.arturogutierrez.openticator.view.activity.BaseActivity;
import com.arturogutierrez.openticator.view.activity.ProxyActivity;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = { ApplicationModule.class })
public interface ApplicationComponent {

  void inject(ProxyActivity proxyActivity);

  void inject(BaseActivity baseActivity);

  Context context();

  LayoutInflater layoutInflater();

  ThreadExecutor threadExecutor();

  PostExecutionThread postExecutionThread();

  SharedPreferences sharedPreferences();

  Preferences preferences();

  AccountRepository provideAccountRepository();
}
