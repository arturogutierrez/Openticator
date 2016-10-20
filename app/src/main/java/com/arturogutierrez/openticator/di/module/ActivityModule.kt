package com.arturogutierrez.openticator.di.module

import android.app.Activity
import com.arturogutierrez.openticator.di.PerActivity
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: Activity) {

  @Provides
  @PerActivity
  internal fun activity(): Activity {
    return activity
  }
}
