package com.arturogutierrez.openticator.application

import android.util.Base64
import com.arturogutierrez.openticator.di.component.ApplicationDebugComponent
import com.arturogutierrez.openticator.di.component.DaggerApplicationDebugComponent
import com.arturogutierrez.openticator.di.module.ApplicationModule
import com.arturogutierrez.openticator.storage.preferences.Preferences
import com.facebook.stetho.Stetho
import com.uphyca.stetho_realm.RealmInspectorModulesProvider
import javax.inject.Inject

class OpenticatorDebugApplication : OpenticatorApplication() {

  override val applicationComponent: ApplicationDebugComponent by lazy {
    DaggerApplicationDebugComponent.builder()
        .applicationModule(ApplicationModule(this))
        .build()
  }

  @Inject
  lateinit var preferences: Preferences

  override fun onCreate() {
    super.onCreate()

    initializeInjector()
    initializeStetho()
  }

  private fun initializeInjector() {
    applicationComponent.inject(this)
  }

  private fun initializeStetho() {
    val masterPassword = preferences.masterPassword
    if (masterPassword.isNullOrEmpty()) {
      return
    }

    val encryptionKey = Base64.decode(preferences.masterPassword, Base64.NO_WRAP)
    val realmInspectorModulesProvider = RealmInspectorModulesProvider.builder(this)
        .withEncryptionKey("default.realm", encryptionKey)
        .withMetaTables()
        .build()

    Stetho.initialize(Stetho.newInitializerBuilder(this)
        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
        .enableWebKitInspector(realmInspectorModulesProvider)
        .build())
  }
}
