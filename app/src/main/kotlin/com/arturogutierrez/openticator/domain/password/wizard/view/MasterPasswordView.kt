package com.arturogutierrez.openticator.domain.password.wizard.view

interface MasterPasswordView {

  fun showWeakPasswordError()

  fun showMismatchPasswordsError()

  fun closeWizard()
}
