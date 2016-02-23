package com.arturogutierrez.openticator.domain.account.list;

import com.arturogutierrez.openticator.domain.account.interactor.DeleteAccountsInteractor;
import com.arturogutierrez.openticator.domain.account.model.Account;
import com.arturogutierrez.openticator.interactor.DefaultSubscriber;
import com.arturogutierrez.openticator.view.presenter.Presenter;
import java.util.Set;
import javax.inject.Inject;

public class AccountEditModePresenter extends DefaultSubscriber<Void> implements Presenter {

  private final DeleteAccountsInteractor deleteAccountsInteractor;
  private AccountEditModeView view;

  @Inject
  public AccountEditModePresenter(DeleteAccountsInteractor deleteAccountsInteractor) {
    this.deleteAccountsInteractor = deleteAccountsInteractor;
  }

  public void setView(AccountEditModeView view) {
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
    deleteAccountsInteractor.unsubscribe();
  }

  public void deleteAccounts(Set<Account> selectedAccounts) {
    deleteAccountsInteractor.configure(selectedAccounts);
    deleteAccountsInteractor.execute(this);

    view.dismissActionMode();
  }

  public void onSelectedAccounts(Set<Account> selectedAccounts) {
    if (selectedAccounts.size() == 0) {
      view.dismissActionMode();
      return;
    }

    view.showEditButton(selectedAccounts.size() == 1);
  }
}
