package com.arturogutierrez.openticator.domain.account.add;

import com.arturogutierrez.openticator.domain.account.interactor.AddAccountInteractor;
import com.arturogutierrez.openticator.domain.account.model.Account;
import com.arturogutierrez.openticator.interactor.DefaultSubscriber;
import com.arturogutierrez.openticator.view.presenter.Presenter;
import javax.inject.Inject;

public class AddAccountFormPresenter extends DefaultSubscriber<Account> implements Presenter {

  private final AddAccountInteractor addAccountInteractorInteractor;
  private AddAccountView view;

  @Inject
  public AddAccountFormPresenter(AddAccountInteractor addAccountInteractorInteractor) {
    this.addAccountInteractorInteractor = addAccountInteractorInteractor;
  }

  public void setView(AddAccountView view) {
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
    addAccountInteractorInteractor.unsubscribe();
  }

  public void createTimeBasedAccount(String accountName, String accountSecret) {
    if (accountName == null || accountName.trim().length() == 0) {
      view.showFieldError(AddAccountView.FieldError.NAME);
    } else if (accountSecret == null || accountSecret.trim().length() == 0) {
      // TODO: Add validation to support only base 32 chars
      view.showFieldError(AddAccountView.FieldError.SECRET);
    } else {
      addAccountInteractorInteractor.configure(accountName.trim(), accountSecret.trim());
      addAccountInteractorInteractor.execute(this);
    }
  }

  @Override
  public void onNext(Account account) {
    view.dismissForm();
  }

  @Override
  public void onError(Throwable e) {
    view.unableToAddAccount();
  }
}
