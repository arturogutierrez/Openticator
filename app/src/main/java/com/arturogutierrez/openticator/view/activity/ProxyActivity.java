package com.arturogutierrez.openticator.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.arturogutierrez.openticator.application.OpenticatorApplication;
import com.arturogutierrez.openticator.di.component.ApplicationComponent;
import com.arturogutierrez.openticator.domain.bootstrap.InitialScreenSelector;
import com.arturogutierrez.openticator.domain.bootstrap.StorageInitializator;
import com.arturogutierrez.openticator.domain.navigator.Navigator;
import javax.inject.Inject;

public class ProxyActivity extends AppCompatActivity {

  @Inject
  Navigator navigator;
  @Inject
  InitialScreenSelector screenSelector;
  @Inject
  StorageInitializator storageInitializator;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    initializeInjector();
  }

  @Override
  protected void onResume() {
    super.onResume();

    if (screenSelector.shouldShowWizard()) {
      navigator.goToInitialWizard(this);
    } else {
      configureApplication();
      navigator.goToAccountList(this);
    }

    overridePendingTransition(0, 0);
    finish();
  }

  private void initializeInjector() {
    ApplicationComponent applicationComponent =
        ((OpenticatorApplication) getApplication()).getApplicationComponent();
    applicationComponent.inject(this);
  }

  private void configureApplication() {
    storageInitializator.configureStorage();
  }
}
