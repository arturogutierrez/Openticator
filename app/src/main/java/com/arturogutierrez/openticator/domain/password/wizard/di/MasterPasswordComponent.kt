package com.arturogutierrez.openticator.domain.password.wizard.di

import com.arturogutierrez.openticator.di.PerActivity
import com.arturogutierrez.openticator.di.component.ActivityComponent
import com.arturogutierrez.openticator.di.component.ApplicationComponent
import com.arturogutierrez.openticator.di.module.ActivityModule
import com.arturogutierrez.openticator.domain.password.wizard.view.MasterPasswordFragment
import dagger.Component

@PerActivity
@Component(dependencies = arrayOf(ApplicationComponent::class),
    modules = arrayOf(ActivityModule::class, MasterPasswordModule::class))
interface MasterPasswordComponent : ActivityComponent {
  fun inject(masterPasswordFragment: MasterPasswordFragment)
}
