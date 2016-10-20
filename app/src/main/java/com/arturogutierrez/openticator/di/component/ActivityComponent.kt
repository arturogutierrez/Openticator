package com.arturogutierrez.openticator.di.component

import android.app.Activity
import com.arturogutierrez.openticator.di.PerActivity
import com.arturogutierrez.openticator.di.module.ActivityModule
import dagger.Component

@PerActivity
@Component(dependencies = arrayOf(ApplicationComponent::class), modules = arrayOf(ActivityModule::class))
interface ActivityComponent {
  fun activity(): Activity
}
