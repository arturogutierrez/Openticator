package com.arturogutierrez.openticator.application;

import android.app.Application;
import com.arturogutierrez.openticator.di.component.ApplicationComponent;
import com.arturogutierrez.openticator.di.component.DaggerApplicationComponent;
import com.arturogutierrez.openticator.di.module.ApplicationModule;

public class OpenticatorApplication extends Application {

  private ApplicationComponent applicationComponent;

  @Override
  public void onCreate() {
    super.onCreate();

    initialize();
  }

  private void initialize() {
    initializeDependencyInjector();
  }

  private void initializeDependencyInjector() {
    applicationComponent =
        DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
  }

  public ApplicationComponent getApplicationComponent() {
    return applicationComponent;
  }
}
