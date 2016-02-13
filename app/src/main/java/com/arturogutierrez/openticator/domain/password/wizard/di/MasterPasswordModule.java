package com.arturogutierrez.openticator.domain.password.wizard.di;

import com.arturogutierrez.openticator.di.PerActivity;
import com.arturogutierrez.openticator.domain.Preferences;
import com.arturogutierrez.openticator.domain.password.PasswordSerializer;
import com.arturogutierrez.openticator.domain.password.PasswordSerializerImpl;
import com.arturogutierrez.openticator.domain.password.interactor.CreateMasterPasswordInteractor;
import dagger.Module;
import dagger.Provides;

@Module
public class MasterPasswordModule {

  public MasterPasswordModule() {

  }

  @Provides
  PasswordSerializer providePasswordSerializer(PasswordSerializerImpl passwordSerializer) {
    return passwordSerializer;
  }

  @Provides
  @PerActivity
  CreateMasterPasswordInteractor provideCreateMasterPasswordInteractor(Preferences preferences,
      PasswordSerializer passwordSerializer) {
    return new CreateMasterPasswordInteractor(preferences, passwordSerializer);
  }
}
