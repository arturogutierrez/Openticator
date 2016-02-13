package com.arturogutierrez.openticator.domain.password.interactor;

import com.arturogutierrez.openticator.domain.Preferences;
import com.arturogutierrez.openticator.domain.password.PasswordSerializer;

public class CreateMasterPasswordInteractor {

  private final Preferences preferences;
  private final PasswordSerializer passwordSerializer;

  public CreateMasterPasswordInteractor(Preferences preferences,
      PasswordSerializer passwordSerializer) {
    this.preferences = preferences;
    this.passwordSerializer = passwordSerializer;
  }

  public void createMasterPassword(String plainPassword) {
    String encodedPassword = passwordSerializer.encodePassword(plainPassword);

    if (encodedPassword == null) {
      // it's a bad practice store the password in plain
      // but this should not happen (it's just in case)
      encodedPassword = plainPassword;
    }

    preferences.setMasterPassword(encodedPassword);
  }
}
