package com.arturogutierrez.openticator.domain.password.wizard;

import com.arturogutierrez.openticator.domain.bootstrap.StorageInitializator;
import com.arturogutierrez.openticator.domain.password.interactor.CreateMasterPasswordInteractor;
import com.arturogutierrez.openticator.domain.password.wizard.view.MasterPasswordView;
import com.arturogutierrez.openticator.view.presenter.Presenter;
import javax.inject.Inject;

public class MasterPasswordPresenter implements Presenter {

  private final CreateMasterPasswordInteractor createMasterPasswordInteractor;
  private final StorageInitializator storageInitializator;
  private MasterPasswordView view;

  @Inject
  public MasterPasswordPresenter(CreateMasterPasswordInteractor createMasterPasswordInteractor,
      StorageInitializator storageInitializator) {
    this.createMasterPasswordInteractor = createMasterPasswordInteractor;
    this.storageInitializator = storageInitializator;
  }

  public void setView(MasterPasswordView view) {
    this.view = view;
  }

  @Override
  public void resume() {

  }

  @Override
  public void pause() {

  }

  @Override
  public void destroy() {

  }

  public void createMasterPassword(String password, String confirmPassword) {
    if (!isStrongPassword(password)) {
      view.showWeakPasswordError();
    } else if (!password.equals(confirmPassword)) {
      view.showMismatchPasswordsError();
    } else {
      createMasterPasswordInteractor.createMasterPassword(password);
      storageInitializator.configureStorage();
      view.closeWizard();
    }
  }

  private boolean isStrongPassword(String password) {
    if (password == null) {
      return false;
    }

    return password.length() >= 10;
  }
}
