package com.arturogutierrez.openticator.domain.account.add

interface AddAccountView {

  enum class FieldError {
    NAME, SECRET
  }

  fun dismissForm()

  fun unableToAddAccount()

  fun showFieldError(fieldError: FieldError)
}
