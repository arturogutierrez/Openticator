package com.arturogutierrez.openticator.domain.account.add

import com.arturogutierrez.openticator.view.presenter.Presenter

interface AddAccountView : Presenter.View {

  enum class FieldError {
    NAME, SECRET
  }

  fun dismissForm()

  fun unableToAddAccount()

  fun showFieldError(fieldError: FieldError)
}
