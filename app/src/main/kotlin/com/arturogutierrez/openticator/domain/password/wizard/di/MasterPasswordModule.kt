package com.arturogutierrez.openticator.domain.password.wizard.di

import com.arturogutierrez.openticator.di.PerActivity
import com.arturogutierrez.openticator.storage.preferences.Preferences
import com.arturogutierrez.openticator.domain.password.PasswordSerializer
import com.arturogutierrez.openticator.domain.password.PasswordSerializerImpl
import com.arturogutierrez.openticator.domain.password.interactor.CreateMasterPasswordInteractor
import dagger.Module
import dagger.Provides

@Module
class MasterPasswordModule {

  @Provides
  internal fun providePasswordSerializer(): PasswordSerializer {
    return PasswordSerializerImpl()
  }

  @Provides
  @PerActivity
  internal fun provideCreateMasterPasswordInteractor(preferences: Preferences,
                                                     passwordSerializer: PasswordSerializer): CreateMasterPasswordInteractor {
    return CreateMasterPasswordInteractor(preferences, passwordSerializer)
  }
}
