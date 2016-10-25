package com.arturogutierrez.openticator.di.module

import android.app.Activity
import com.arturogutierrez.openticator.di.PerActivity
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: Activity) {

  @Provides
  @PerActivity
  fun activity(): Activity = activity
}
