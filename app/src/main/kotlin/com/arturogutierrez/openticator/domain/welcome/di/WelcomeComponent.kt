package com.arturogutierrez.openticator.domain.welcome.di

import com.arturogutierrez.openticator.di.PerActivity
import com.arturogutierrez.openticator.di.component.ActivityComponent
import com.arturogutierrez.openticator.di.component.ApplicationComponent
import com.arturogutierrez.openticator.di.module.ActivityModule
import com.arturogutierrez.openticator.domain.welcome.activity.WelcomeActivity
import dagger.Component

@PerActivity
@Component(dependencies = arrayOf(ApplicationComponent::class), modules = arrayOf(ActivityModule::class, WelcomeModule::class))
interface WelcomeComponent : ActivityComponent {

  fun inject(welcomeActivity: WelcomeActivity)
}
