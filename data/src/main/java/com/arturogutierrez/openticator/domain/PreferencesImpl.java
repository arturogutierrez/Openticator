package com.arturogutierrez.openticator.domain;

import android.content.SharedPreferences;
import javax.inject.Inject;

public class PreferencesImpl implements Preferences {

  private static final String MASTER_PASSWORD_ID = "MASTER_ID";

  private final SharedPreferences sharedPreferences;

  @Inject
  public PreferencesImpl(SharedPreferences sharedPreferences) {
    this.sharedPreferences = sharedPreferences;
  }

  @Override
  public void reset() {
    sharedPreferences.edit().clear().apply();
  }

  @Override
  public String getMasterPassword() {
    return sharedPreferences.getString(MASTER_PASSWORD_ID, null);
  }

  @Override
  public void setMasterPassword(String plainMasterPassword) {
    sharedPreferences.edit().putString(MASTER_PASSWORD_ID, plainMasterPassword).apply();
  }
}
