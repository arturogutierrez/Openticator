package com.arturogutierrez.openticator.di.component

import com.arturogutierrez.openticator.application.OpenticatorDebugApplication
import com.arturogutierrez.openticator.di.module.ApplicationModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationDebugComponent : ApplicationComponent {

  fun inject(application: OpenticatorDebugApplication)
}
