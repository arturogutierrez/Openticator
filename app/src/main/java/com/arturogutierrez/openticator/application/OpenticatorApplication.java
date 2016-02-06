package com.arturogutierrez.openticator.application;

import android.app.Application;
import com.arturogutierrez.openticator.di.component.ApplicationComponent;
import com.arturogutierrez.openticator.di.component.DaggerApplicationComponent;
import com.arturogutierrez.openticator.di.module.ApplicationModule;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class OpenticatorApplication extends Application {

  private ApplicationComponent applicationComponent;

  @Override
  public void onCreate() {
    super.onCreate();

    initialize();
  }

  private void initialize() {
    initializeDependencyInjector();
    initializeRealm();
  }

  private void initializeDependencyInjector() {
    applicationComponent =
        DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
  }

  private void initializeRealm() {
    RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this).build();
    Realm.setDefaultConfiguration(realmConfiguration);
  }

  public ApplicationComponent getApplicationComponent() {
    return applicationComponent;
  }
}
