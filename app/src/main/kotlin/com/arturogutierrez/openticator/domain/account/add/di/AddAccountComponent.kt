package com.arturogutierrez.openticator.domain.account.add.di

import com.arturogutierrez.openticator.di.PerActivity
import com.arturogutierrez.openticator.di.component.ActivityComponent
import com.arturogutierrez.openticator.di.component.ApplicationComponent
import com.arturogutierrez.openticator.di.module.ActivityModule
import com.arturogutierrez.openticator.domain.account.add.view.AddAccountFormFragment
import com.arturogutierrez.openticator.domain.account.add.view.AddAccountFromCameraFragment
import dagger.Component

@PerActivity
@Component(dependencies = arrayOf(ApplicationComponent::class), modules = arrayOf(ActivityModule::class, AddAccountModule::class))
interface AddAccountComponent : ActivityComponent {

  fun inject(addAccountFormFragment: AddAccountFormFragment)

  fun inject(addAccountFromCameraFragment: AddAccountFromCameraFragment)
}
