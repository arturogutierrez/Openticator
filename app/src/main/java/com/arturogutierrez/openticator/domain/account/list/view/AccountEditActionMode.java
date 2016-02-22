package com.arturogutierrez.openticator.domain.account.list.view;

import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.arturogutierrez.openticator.R;
import com.arturogutierrez.openticator.domain.account.list.AccountEditModePresenter;
import com.arturogutierrez.openticator.domain.account.list.AccountEditModeView;
import com.arturogutierrez.openticator.domain.account.list.di.AccountListComponent;
import com.arturogutierrez.openticator.domain.account.model.Account;
import java.util.Set;
import javax.inject.Inject;
import rx.Subscription;

public class AccountEditActionMode implements ActionMode.Callback, AccountEditModeView {

  @Inject
  AccountEditModePresenter presenter;

  private final AccountsAdapter accountsAdapter;
  private Subscription accountsSubscription;
  private ActionMode actionMode;
  private MenuItem menuItemEdit;

  public AccountEditActionMode(AccountListComponent accountListComponent,
      AccountsAdapter accountsAdapter) {
    this.accountsAdapter = accountsAdapter;

    initialize(accountListComponent);
  }

  private void initialize(AccountListComponent accountListComponent) {
    initializeInjector(accountListComponent);

    accountsSubscription = accountsAdapter.selectedAccounts().subscribe(this::onSelectedAccounts);
    presenter.setView(this);
  }

  private void initializeInjector(AccountListComponent accountListComponent) {
    accountListComponent.inject(this);
  }

  @Override
  public boolean onCreateActionMode(ActionMode mode, Menu menu) {
    configureActionMenu(mode.getMenuInflater(), menu);
    this.actionMode = mode;
    return true;
  }

  @Override
  public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
    return false;
  }

  @Override
  public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_delete:
        deleteSelectedAccounts();
        return true;
    }

    return false;
  }

  @Override
  public void onDestroyActionMode(ActionMode mode) {
    menuItemEdit = null;
    accountsAdapter.setEditMode(false);
    accountsSubscription.unsubscribe();
  }

  private void configureActionMenu(MenuInflater inflater, Menu menu) {
    inflater.inflate(R.menu.list_account, menu);
    menuItemEdit = menu.findItem(R.id.action_edit);
  }

  private void onSelectedAccounts(Set<Account> selectedAccounts) {
    presenter.onSelectedAccounts(selectedAccounts);
  }

  private void deleteSelectedAccounts() {
    presenter.deleteAccounts(accountsAdapter.getSelectedAccounts());
  }

  @Override
  public void dismissActionMode() {
    actionMode.finish();
  }

  @Override
  public void showEditButton(boolean isVisible) {
    menuItemEdit.setVisible(isVisible);
  }
}
