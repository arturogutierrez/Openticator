package com.arturogutierrez.openticator.domain.password.wizard.view

import com.arturogutierrez.openticator.view.presenter.Presenter

interface MasterPasswordView: Presenter.View {

  fun showWeakPasswordError()

  fun showMismatchPasswordsError()

  fun closeWizard()
}
