package com.arturogutierrez.openticator.domain.account.list;

import com.arturogutierrez.openticator.domain.account.interactor.DeleteAccountsInteractor;
import com.arturogutierrez.openticator.domain.account.interactor.UpdateAccountInteractor;
import com.arturogutierrez.openticator.domain.account.model.Account;
import com.arturogutierrez.openticator.interactor.DefaultSubscriber;
import com.arturogutierrez.openticator.view.presenter.Presenter;
import java.util.Set;
import javax.inject.Inject;

public class AccountEditModePresenter extends DefaultSubscriber<Void> implements Presenter {

  private final DeleteAccountsInteractor deleteAccountsInteractor;
  private final UpdateAccountInteractor updateAccountInteractor;
  private AccountEditModeView view;

  @Inject
  public AccountEditModePresenter(UpdateAccountInteractor updateAccountInteractor,
      DeleteAccountsInteractor deleteAccountsInteractor) {
    this.updateAccountInteractor = updateAccountInteractor;
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
    deleteAccountsInteractor.execute(new DeleteAccountsSubscriber());
  }

  public void onSelectedAccounts(Set<Account> selectedAccounts) {
    if (selectedAccounts.size() == 0) {
      view.dismissActionMode();
      return;
    }

    view.showEditButton(selectedAccounts.size() == 1);
  }

  public void updateAccount(Account account, String newName) {
    if (newName.length() > 0) {
      updateAccountInteractor.configure(account, newName);
      updateAccountInteractor.execute(new UpdateAccountSubscriber());
    }

    view.dismissActionMode();
  }

  private class UpdateAccountSubscriber extends DefaultSubscriber<Account> {
    @Override
    public void onNext(Account account) {
      view.dismissActionMode();
    }
  }

  private class DeleteAccountsSubscriber extends DefaultSubscriber<Void> {
    @Override
    public void onNext(Void aVoid) {
      view.dismissActionMode();
    }
  }
}
