package com.arturogutierrez.openticator.di.module;

import android.app.Activity;
import com.arturogutierrez.openticator.di.PerActivity;
import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
  private final Activity activity;

  public ActivityModule(Activity activity) {
    this.activity = activity;
  }

  @Provides
  @PerActivity
  Activity activity() {
    return activity;
  }
}
