package com.arturogutierrez.openticator.domain.account.add;

public interface AddAccountView {

  enum FieldError {
    NAME,
    SECRET
  }

  void dismissForm();

  void unableToAddAccount();

  void showFieldError(FieldError fieldError);
}
