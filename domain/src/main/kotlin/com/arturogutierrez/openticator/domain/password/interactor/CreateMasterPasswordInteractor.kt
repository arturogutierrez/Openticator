package com.arturogutierrez.openticator.domain.password.interactor

import com.arturogutierrez.openticator.domain.password.PasswordSerializer
import com.arturogutierrez.openticator.storage.preferences.Preferences

class CreateMasterPasswordInteractor(
    private val preferences: Preferences,
    private val passwordSerializer: PasswordSerializer) {

  fun createMasterPassword(plainPassword: String) {
    var encodedPassword = passwordSerializer.encodePassword(plainPassword)

    if (encodedPassword == null) {
      // it's a bad practice store the password in plain
      // but this should not happen (it's just in case)
      encodedPassword = plainPassword
    }

    preferences.masterPassword = encodedPassword
  }
}
