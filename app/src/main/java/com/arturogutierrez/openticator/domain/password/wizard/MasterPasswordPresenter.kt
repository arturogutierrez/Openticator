package com.arturogutierrez.openticator.domain.password.wizard

import com.arturogutierrez.openticator.domain.bootstrap.StorageInitializator
import com.arturogutierrez.openticator.domain.password.interactor.CreateMasterPasswordInteractor
import com.arturogutierrez.openticator.domain.password.wizard.view.MasterPasswordView
import com.arturogutierrez.openticator.view.presenter.Presenter
import javax.inject.Inject

class MasterPasswordPresenter @Inject constructor(val createMasterPasswordInteractor: CreateMasterPasswordInteractor,
                                                  val storageInitializator: StorageInitializator) : Presenter {
  private lateinit var view: MasterPasswordView

  fun setView(view: MasterPasswordView) {
    this.view = view
  }

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

  private fun isStrongPassword(password: String?): Boolean {
    if (password == null) {
      return false
    }

    return password.length >= 10
  }
}
