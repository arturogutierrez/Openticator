package com.arturogutierrez.openticator.application

import android.support.multidex.MultiDexApplication
import com.arturogutierrez.openticator.BuildConfig
import com.arturogutierrez.openticator.R
import com.arturogutierrez.openticator.di.component.ApplicationComponent
import com.arturogutierrez.openticator.di.component.DaggerApplicationComponent
import com.arturogutierrez.openticator.di.module.ApplicationModule
import com.arturogutierrez.openticator.domain.bootstrap.StorageInitializator
import com.crashlytics.android.Crashlytics
import com.karumi.dexter.Dexter
import io.fabric.sdk.android.Fabric
import io.flowup.FlowUp
import javax.inject.Inject

class OpenticatorApplication : MultiDexApplication() {

  val applicationComponent: ApplicationComponent = DaggerApplicationComponent.builder().applicationModule(ApplicationModule(this)).build()

  @Inject
  lateinit var storageInitializator: StorageInitializator

  override fun onCreate() {
    super.onCreate()
    initialize()
  }

  private fun initialize() {
    initializeInjector()
    initializeFlowUp()
    initializeFabric()
    initializeStorage()
    initializeDexter()
  }

  private fun initializeFlowUp() {
    FlowUp.Builder.with(this)
        .apiKey(getString(R.string.flow_up_api_key))
        .forceReports(BuildConfig.DEBUG)
        .start()
  }

  private fun initializeInjector() {
    applicationComponent.inject(this)
  }

  private fun initializeFabric() {
    val packageName = getString(R.string.default_package_name)
    val fabric = Fabric.Builder(this).kits(Crashlytics()).appIdentifier(packageName).build()
    Fabric.with(fabric)
  }

  private fun initializeStorage() {
    storageInitializator.configureStorage()
  }

  private fun initializeDexter() {
    Dexter.initialize(this)
  }
}
