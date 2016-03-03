package com.arturogutierrez.openticator.domain.account.list.view;

import android.app.Activity;
import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.afollestad.materialdialogs.MaterialDialog;
import com.arturogutierrez.openticator.R;
import com.arturogutierrez.openticator.domain.account.list.AccountEditModePresenter;
import com.arturogutierrez.openticator.domain.account.list.AccountEditModeView;
import com.arturogutierrez.openticator.domain.account.list.di.AccountListComponent;
import com.arturogutierrez.openticator.domain.account.model.Account;
import com.arturogutierrez.openticator.domain.category.model.Category;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import rx.Subscription;

public class AccountEditActionMode implements ActionMode.Callback, AccountEditModeView {

  @Inject
  Activity activity;
  @Inject
  AccountEditModePresenter presenter;

  private final AccountsAdapter accountsAdapter;
  private Subscription accountsSubscription;
  private ActionMode actionMode;
  private MenuItem menuItemCategory;
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
      case R.id.action_edit:
        changeNameForSelectedAccount();
        return true;
      case R.id.action_category:
        changeCategoryForSelectedAccount();
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
    menuItemCategory = menu.findItem(R.id.action_category);
  }

  private void onSelectedAccounts(Set<Account> selectedAccounts) {
    presenter.onSelectedAccounts(selectedAccounts);
  }

  private void deleteSelectedAccounts() {
    presenter.deleteAccounts(accountsAdapter.getSelectedAccounts());
  }

  private void changeNameForSelectedAccount() {
    Account selectedAccount = accountsAdapter.getSelectedAccounts().iterator().next();

    new MaterialDialog.Builder(activity).title(R.string.rename_account)
        .input(null, selectedAccount.getName(), (dialog, input) -> {
          presenter.updateAccount(selectedAccount, input.toString());
        })
        .show();
  }

  private void changeCategoryForSelectedAccount() {
    Account selectedAccount = accountsAdapter.getSelectedAccounts().iterator().next();

    presenter.pickCategoryForAccount(selectedAccount);
  }

  @Override
  public void dismissActionMode() {
    actionMode.finish();
  }

  @Override
  public void showCategoryButton(boolean isVisible) {
    menuItemCategory.setVisible(isVisible);
  }

  @Override
  public void showEditButton(boolean isVisible) {
    menuItemEdit.setVisible(isVisible);
  }

  @Override
  public void showChooseEmptyCategory(Account account) {
    new MaterialDialog.Builder(activity).title(R.string.add_to_category)
        .content(R.string.no_categories)
        .positiveText(R.string.create_new_category)
        .onPositive((dialog, which) -> {
          dialog.dismiss();
          showAddNewCategory(account);
        })
        .show();
  }

  private void showAddNewCategory(Account account) {
    new MaterialDialog.Builder(activity).title(R.string.add_to_category)
        .input(R.string.category, 0, (dialog, input) -> {
          dialog.dismiss();
          presenter.createCategory(input.toString(), account);
        })
        .show();
  }

  @Override
  public void showChooseCategory(List<Category> categories, Account account) {
    List<String> stringCategories = new ArrayList<>(categories.size());
    for (Category category : categories) {
      stringCategories.add(category.getName());
    }

    new MaterialDialog.Builder(activity).title(R.string.add_to_category)
        .items(stringCategories)
        .itemsCallback((dialog, itemView, which, text) -> {
          Category category = categories.get(which);
          presenter.addAccountToCategory(category, account);
        })
        .positiveText(R.string.create_new_category)
        .onPositive((dialog, which) -> {
          dialog.dismiss();
          showAddNewCategory(account);
        })
        .show();
  }
}
