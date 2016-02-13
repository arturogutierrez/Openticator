package com.arturogutierrez.openticator.domain.bootstrap;

import com.arturogutierrez.openticator.domain.DatabaseConfigurator;
import com.arturogutierrez.openticator.domain.Preferences;
import javax.inject.Inject;

public class StorageInitializator {

  private final Preferences preferences;
  private final DatabaseConfigurator databaseConfigurator;

  @Inject
  public StorageInitializator(Preferences preferences, DatabaseConfigurator databaseConfigurator) {
    this.preferences = preferences;
    this.databaseConfigurator = databaseConfigurator;
  }

  public void configureStorage() {
    String passwordInBase64 = preferences.getMasterPassword();
    if (passwordInBase64 != null) {
      databaseConfigurator.configure(passwordInBase64);
    }
  }
}
