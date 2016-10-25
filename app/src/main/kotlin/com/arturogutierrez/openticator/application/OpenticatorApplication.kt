package com.arturogutierrez.openticator.application

import android.app.Application
import com.arturogutierrez.openticator.R
import com.arturogutierrez.openticator.di.component.ApplicationComponent
import com.arturogutierrez.openticator.di.component.DaggerApplicationComponent
import com.arturogutierrez.openticator.di.module.ApplicationModule
import com.crashlytics.android.Crashlytics
import com.karumi.dexter.Dexter
import io.fabric.sdk.android.Fabric

class OpenticatorApplication : Application() {

  val applicationComponent: ApplicationComponent = DaggerApplicationComponent.builder().applicationModule(ApplicationModule(this)).build()

  override fun onCreate() {
    super.onCreate()
    initialize()
  }

  private fun initialize() {
    initializeFabric()
    initializeDexter()
  }

  private fun initializeFabric() {
    val packageName = getString(R.string.default_package_name)
    val fabric = Fabric.Builder(this).kits(Crashlytics()).appIdentifier(packageName).build()
    Fabric.with(fabric)
  }

  private fun initializeDexter() {
    Dexter.initialize(this)
  }
}
