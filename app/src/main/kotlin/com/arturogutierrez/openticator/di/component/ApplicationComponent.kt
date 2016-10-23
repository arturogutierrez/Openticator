package com.arturogutierrez.openticator.di.component

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import com.arturogutierrez.openticator.di.module.ApplicationModule
import com.arturogutierrez.openticator.storage.database.DatabaseConfigurator
import com.arturogutierrez.openticator.storage.preferences.Preferences
import com.arturogutierrez.openticator.domain.account.repository.AccountRepository
import com.arturogutierrez.openticator.domain.category.CategorySelector
import com.arturogutierrez.openticator.domain.category.repository.CategoryRepository
import com.arturogutierrez.openticator.domain.issuer.repository.IssuerRepository
import com.arturogutierrez.openticator.executor.PostExecutionThread
import com.arturogutierrez.openticator.executor.ThreadExecutor
import com.arturogutierrez.openticator.storage.clipboard.ClipboardRepository
import com.arturogutierrez.openticator.view.activity.BaseActivity
import com.arturogutierrez.openticator.view.activity.ProxyActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {
  fun inject(proxyActivity: ProxyActivity)

  fun inject(baseActivity: BaseActivity)

  fun context(): Context

  fun layoutInflater(): LayoutInflater

  fun threadExecutor(): ThreadExecutor

  fun postExecutionThread(): PostExecutionThread

  fun sharedPreferences(): SharedPreferences

  fun preferences(): Preferences

  fun databaseConfigurator(): DatabaseConfigurator

  fun provideAccountRepository(): AccountRepository

  fun provideCategoryRepository(): CategoryRepository

  fun provideIssuerRepository(): IssuerRepository

  fun provideCategorySelector(): CategorySelector

  fun provideClipboardRepository(): ClipboardRepository
}
