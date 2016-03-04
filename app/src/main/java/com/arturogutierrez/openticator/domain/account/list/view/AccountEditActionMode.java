package com.arturogutierrez.openticator.domain.account.list.view;

import android.app.Activity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import com.afollestad.materialdialogs.MaterialDialog;
import com.arturogutierrez.openticator.R;
import com.arturogutierrez.openticator.domain.account.list.AccountEditModePresenter;
import com.arturogutierrez.openticator.domain.account.list.AccountEditModeView;
import com.arturogutierrez.openticator.domain.account.list.adapter.AccountsAdapter;
import com.arturogutierrez.openticator.domain.account.list.adapter.IssuersAdapter;
import com.arturogutierrez.openticator.domain.account.list.di.AccountListComponent;
import com.arturogutierrez.openticator.domain.account.model.Account;
import com.arturogutierrez.openticator.domain.category.model.Category;
import com.arturogutierrez.openticator.domain.issuer.IssuerDecorator;
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
  @Inject
  LayoutInflater layoutInflater;

  private final AccountsAdapter accountsAdapter;
  private Subscription accountsSubscription;
  private ActionMode actionMode;
  private MenuItem menuItemDelete;
  private MenuItem menuItemCategory;
  private MenuItem menuItemEdit;
  private MenuItem menuItemLogo;

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
    menuItemDelete.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    menuItemEdit.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    menuItemCategory.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    menuItemLogo.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    return true;
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
      case R.id.action_logo:
        changeLogoForSelectedAccount();
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
    inflater.inflate(R.menu.account_edit_action_mode, menu);
    menuItemDelete = menu.findItem(R.id.action_delete);
    menuItemEdit = menu.findItem(R.id.action_edit);
    menuItemCategory = menu.findItem(R.id.action_category);
    menuItemLogo = menu.findItem(R.id.action_logo);
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

  private void changeLogoForSelectedAccount() {
    Account selectedAccount = accountsAdapter.getSelectedAccounts().iterator().next();
    presenter.pickLogoForAccount(selectedAccount);
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
  public void showLogoButton(boolean isVisible) {
    menuItemLogo.setVisible(isVisible);
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

  @Override
  public void showChooseLogo(List<IssuerDecorator> issuers, Account account) {
    View logosView = layoutInflater.inflate(R.layout.dialog_issuers, null);
    RecyclerView recyclerView = (RecyclerView) logosView.findViewById(R.id.rv_logos);
    GridLayoutManager gridLayoutManager = new GridLayoutManager(activity, 3);
    recyclerView.setLayoutManager(gridLayoutManager);

    MaterialDialog dialog = new MaterialDialog.Builder(activity).title(R.string.choose_logo)
        .customView(logosView, false)
        .negativeText(android.R.string.cancel)
        .show();

    recyclerView.setAdapter(new IssuersAdapter(activity, issuers, issuer -> {
      presenter.updateAccount(account, issuer);
      dialog.dismiss();
    }));
  }
}
