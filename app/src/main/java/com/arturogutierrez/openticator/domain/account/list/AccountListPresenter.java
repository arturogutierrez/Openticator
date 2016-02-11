package com.arturogutierrez.openticator.domain.account.list;

import com.arturogutierrez.openticator.domain.account.interactor.GetAccountsInteractor;
import com.arturogutierrez.openticator.domain.account.model.Account;
import com.arturogutierrez.openticator.interactor.DefaultSubscriber;
import com.arturogutierrez.openticator.view.presenter.Presenter;
import java.util.List;
import javax.inject.Inject;

public class AccountListPresenter extends DefaultSubscriber<List<Account>> implements Presenter {

  private final GetAccountsInteractor getAccountsInteractorInteractor;
  private AccountListView view;

  @Inject
  public AccountListPresenter(GetAccountsInteractor getAccountsInteractorInteractor) {
    this.getAccountsInteractorInteractor = getAccountsInteractorInteractor;
  }

  public void setView(AccountListView view) {
    this.view = view;
  }

  @Override
  public void resume() {
    getAccountsInteractorInteractor.execute(this);
  }

  @Override
  public void pause() {
    getAccountsInteractorInteractor.unsubscribe();
  }

  @Override
  public void destroy() {
    getAccountsInteractorInteractor.unsubscribe();
  }

  @Override
  public void onNext(List<Account> accounts) {
    if (accounts == null || accounts.size() == 0) {
      view.viewNoItems();
      return;
    }

    view.renderAccounts(accounts);
  }

  @Override
  public void onError(Throwable e) {
    view.viewNoItems();
  }
}
