package com.arturogutierrez.openticator.domain.sync;

import com.arturogutierrez.openticator.domain.Preferences;
import javax.inject.Inject;

public class SyncTimestamp {

  private final Preferences preferences;

  @Inject
  public SyncTimestamp(Preferences preferences) {
    this.preferences = preferences;
  }

  public void updateLocalModifiedTimestamp() {
    long currentTime = getCurrentTime();
    preferences.setLastLocalModifiedTimestamp(currentTime);
  }

  private long getCurrentTime() {
    // TODO: Apply delta offset time
    return System.currentTimeMillis();
  }
}
