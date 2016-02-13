package com.arturogutierrez.openticator.domain.password.wizard.view;

public interface MasterPasswordView {

  void showWeakPasswordError();

  void showMismatchPasswordsError();

  void closeWizard();
}
