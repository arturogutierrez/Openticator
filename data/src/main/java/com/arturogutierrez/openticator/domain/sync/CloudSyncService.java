package com.arturogutierrez.openticator.domain.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class CloudSyncService extends Service {

  private static CloudSyncAdapter syncAdapter;
  private static final Object syncAdapterLock = new Object();

  @Override
  public void onCreate() {
    super.onCreate();

    synchronized (syncAdapterLock) {
      if (syncAdapter == null) {
        syncAdapter = new CloudSyncAdapter(getApplicationContext(), true);
      }
    }
  }

  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    return syncAdapter.getSyncAdapterBinder();
  }
}
