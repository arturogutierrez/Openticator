package com.arturogutierrez.openticator.domain;

import android.content.SharedPreferences;
import javax.inject.Inject;

public class PreferencesImpl implements Preferences {

  private static final String MASTER_PASSWORD_ID = "MASTER_ID";
  private static final String LAST_SYNC_TIMESTAMP_ID = "LAST_SYNC_TIMESTAMP_ID";
  private static final String LAST_LOCAL_MODIFIED_TIMESTAMP_ID = "LAST_LOCAL_TIMESTAMP_ID";

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
  public void setMasterPassword(String masterPassword) {
    sharedPreferences.edit().putString(MASTER_PASSWORD_ID, masterPassword).apply();
  }

  @Override
  public long getLastSyncTimestamp() {
    return sharedPreferences.getLong(LAST_SYNC_TIMESTAMP_ID, 0);
  }

  @Override
  public void setLastSyncTimestamp(long timestamp) {
    sharedPreferences.edit().putLong(LAST_SYNC_TIMESTAMP_ID, timestamp).apply();
  }

  @Override
  public long getLastLocalModifiedTimestamp() {
    return sharedPreferences.getLong(LAST_LOCAL_MODIFIED_TIMESTAMP_ID, 0);
  }

  @Override
  public void setLastLocalModifiedTimestamp(long timestamp) {
    sharedPreferences.edit().putLong(LAST_LOCAL_MODIFIED_TIMESTAMP_ID, timestamp).apply();
  }
}
