package com.arturogutierrez.openticator.domain.account.list;

import com.arturogutierrez.openticator.domain.account.interactor.GetAccountsInteractor;
import com.arturogutierrez.openticator.view.presenter.Presenter;
import javax.inject.Inject;

public class AccountListPresenter implements Presenter {

  private final GetAccountsInteractor getAccountsInteractorInteractor;

  @Inject
  public AccountListPresenter(GetAccountsInteractor getAccountsInteractorInteractor) {
    this.getAccountsInteractorInteractor = getAccountsInteractorInteractor;
  }

  @Override
  public void resume() {

  }

  @Override
  public void pause() {

  }

  @Override
  public void destroy() {
    getAccountsInteractorInteractor.unsubscribe();
  }
}
