package com.arturogutierrez.openticator.domain.account.add

import com.arturogutierrez.openticator.view.presenter.Presenter

interface AddAccountView : Presenter.View {

  enum class InputError {
    EMPTY_ACCOUNT_NAME, EMPTY_SECRET, INVALID_SECRET
  }

  fun dismissForm()

  fun unableToAddAccount()

  fun showFieldError(inputError: InputError)
}
