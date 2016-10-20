package com.arturogutierrez.openticator.domain.account.list.di

import com.arturogutierrez.openticator.di.PerActivity
import com.arturogutierrez.openticator.di.component.ActivityComponent
import com.arturogutierrez.openticator.di.component.ApplicationComponent
import com.arturogutierrez.openticator.di.module.ActivityModule
import com.arturogutierrez.openticator.domain.account.list.activity.AccountListActivity
import com.arturogutierrez.openticator.domain.account.list.view.AccountEditActionMode
import com.arturogutierrez.openticator.domain.account.list.view.AccountListFragment
import dagger.Component

@PerActivity
@Component(dependencies = arrayOf(ApplicationComponent::class), modules = arrayOf(ActivityModule::class, AccountListModule::class))
interface AccountListComponent : ActivityComponent {

  fun inject(accountListActivity: AccountListActivity)

  fun inject(accountListFragment: AccountListFragment)

  fun inject(accountEditActionMode: AccountEditActionMode)
}
