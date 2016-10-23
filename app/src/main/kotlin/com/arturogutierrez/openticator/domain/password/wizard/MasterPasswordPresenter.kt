package com.arturogutierrez.openticator.domain.password.wizard

import com.arturogutierrez.openticator.domain.bootstrap.StorageInitializator
import com.arturogutierrez.openticator.domain.password.interactor.CreateMasterPasswordInteractor
import com.arturogutierrez.openticator.domain.password.wizard.view.MasterPasswordView
import com.arturogutierrez.openticator.view.presenter.Presenter
import javax.inject.Inject

class MasterPasswordPresenter @Inject constructor(val createMasterPasswordInteractor: CreateMasterPasswordInteractor,
                                                  val storageInitializator: StorageInitializator) : Presenter<MasterPasswordView> {
  lateinit override var view: MasterPasswordView

  fun createMasterPassword(password: String, confirmPassword: String) {
    if (!isStrongPassword(password)) {
      view.showWeakPasswordError()
    } else if (password != confirmPassword) {
      view.showMismatchPasswordsError()
    } else {
      createMasterPasswordInteractor.createMasterPassword(password)
      storageInitializator.configureStorage()
      view.closeWizard()
    }
  }

  private fun isStrongPassword(password: String): Boolean {
    return password.length >= 10
  }
}
