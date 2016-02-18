package com.arturogutierrez.openticator.domain.account.list.view;

import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.arturogutierrez.openticator.R;
import com.arturogutierrez.openticator.domain.account.model.Account;
import java.util.Set;
import rx.Subscription;

public class EditActionMode implements ActionMode.Callback {

  private final AccountsAdapter accountsAdapter;
  private final Subscription accountsSubscription;
  private ActionMode actionMode;
  private MenuItem menuItemDelete;
  private MenuItem menuItemEdit;

  public EditActionMode(AccountsAdapter accountsAdapter) {
    this.accountsAdapter = accountsAdapter;
    this.accountsSubscription =
        accountsAdapter.selectedAccounts().subscribe(this::onSelectedAccounts);
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
    return false;
  }

  @Override
  public void onDestroyActionMode(ActionMode mode) {
    menuItemDelete = null;
    menuItemEdit = null;
    accountsAdapter.setEditMode(false);
    accountsSubscription.unsubscribe();
  }

  private void configureActionMenu(MenuInflater inflater, Menu menu) {
    inflater.inflate(R.menu.list_account, menu);
    menuItemEdit = menu.findItem(R.id.action_edit);
    menuItemDelete = menu.findItem(R.id.action_delete);
  }

  private void onSelectedAccounts(Set<Account> selectedAccounts) {
    if (selectedAccounts.size() == 0) {
      actionMode.finish();
      return;
    }
    menuItemEdit.setVisible(selectedAccounts.size() == 1);
  }
}
