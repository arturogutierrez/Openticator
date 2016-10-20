package com.arturogutierrez.openticator.application

import android.app.Application
import butterknife.ButterKnife
import com.arturogutierrez.openticator.di.component.ApplicationComponent
import com.arturogutierrez.openticator.di.component.DaggerApplicationComponent
import com.arturogutierrez.openticator.di.module.ApplicationModule
import com.karumi.dexter.Dexter

class OpenticatorApplication : Application() {

  lateinit var applicationComponent: ApplicationComponent
    private set

  override fun onCreate() {
    super.onCreate()
    initialize()
  }

  private fun initialize() {
    ButterKnife.setDebug(true)
    initializeDependencyInjector()
    initializeDexter()
  }

  private fun initializeDependencyInjector() {
    applicationComponent = DaggerApplicationComponent.builder().applicationModule(ApplicationModule(this)).build()
  }

  private fun initializeDexter() {
    Dexter.initialize(this)
  }
}
