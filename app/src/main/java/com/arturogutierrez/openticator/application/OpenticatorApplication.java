package com.arturogutierrez.openticator.application;

import android.app.Application;
import com.arturogutierrez.openticator.di.component.ApplicationComponent;
import com.arturogutierrez.openticator.di.component.DaggerApplicationComponent;
import com.arturogutierrez.openticator.di.module.ApplicationModule;
import com.karumi.dexter.Dexter;

public class OpenticatorApplication extends Application {

  private ApplicationComponent applicationComponent;

  @Override
  public void onCreate() {
    super.onCreate();

    initialize();
  }

  public ApplicationComponent getApplicationComponent() {
    return applicationComponent;
  }

  private void initialize() {
    initializeDependencyInjector();
    initializeDexter();
  }

  private void initializeDependencyInjector() {
    applicationComponent =
        DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
  }

  private void initializeDexter() {
    Dexter.initialize(this);
  }
}
