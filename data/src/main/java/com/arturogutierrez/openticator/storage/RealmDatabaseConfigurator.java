package com.arturogutierrez.openticator.storage;

import android.content.Context;
import android.util.Base64;
import com.arturogutierrez.openticator.domain.DatabaseConfigurator;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import javax.inject.Inject;

public class RealmDatabaseConfigurator implements DatabaseConfigurator {

  private final Context context;

  @Inject
  public RealmDatabaseConfigurator(Context context) {
    this.context = context;
  }

  @Override
  public void configure(String passwordInBase64) {
    byte[] encryptionKey = Base64.decode(passwordInBase64, Base64.NO_WRAP);

    configureRealm(encryptionKey);
  }

  private void configureRealm(byte[] encryptionKey) {
    RealmConfiguration realmConfiguration =
        new RealmConfiguration.Builder(context).encryptionKey(encryptionKey).build();
    Realm.setDefaultConfiguration(realmConfiguration);
  }
}
