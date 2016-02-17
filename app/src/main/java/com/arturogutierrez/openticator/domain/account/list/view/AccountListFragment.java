package com.arturogutierrez.openticator.domain.account.list.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import butterknife.Bind;
import com.arturogutierrez.openticator.R;
import com.arturogutierrez.openticator.domain.account.list.AccountListPresenter;
import com.arturogutierrez.openticator.domain.account.list.AccountListView;
import com.arturogutierrez.openticator.domain.account.list.di.AccountListComponent;
import com.arturogutierrez.openticator.domain.account.model.AccountPasscode;
import com.arturogutierrez.openticator.view.fragment.BaseFragment;
import java.util.List;
import javax.inject.Inject;

public class AccountListFragment extends BaseFragment
    implements AccountListView, AccountsAdapter.OnEditListener {

  @Inject
  AccountListPresenter presenter;

  @Bind(R.id.rv_accounts)
  RecyclerView rvAccounts;

  @Bind(R.id.tv_empty_view)
  TextView tvEmptyView;
  ActionBar actionBar;

  private AccountsAdapter accountsAdapter;

  public AccountListFragment() {
    super();
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    initialize();
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        accountsAdapter.setEditMode(false);
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  @Override
  public void onResume() {
    super.onResume();
    presenter.resume();
  }

  @Override
  public void onPause() {
    super.onPause();
    presenter.pause();
    stopCounters();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    presenter.destroy();
  }

  @Override
  protected int getLayoutResource() {
    return R.layout.fragment_account_list;
  }

  @Override
  protected void configureUI() {
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

    rvAccounts.setLayoutManager(linearLayoutManager);
  }

  private void initialize() {
    initializeInjector();
    setHasOptionsMenu(true);
    actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
    presenter.setView(this);
  }

  private void initializeInjector() {
    getComponent(AccountListComponent.class).inject(this);
  }

  @Override
  public void viewNoItems() {
    showEmptyView();
  }

  @Override
  public void renderAccounts(List<AccountPasscode> accounts) {
    showAccountList(accounts);
  }

  @Override
  public void showEditActionButtons() {
    Activity activity = getActivity();
    if (activity != null) {
      activity.startActionMode(new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
          MenuInflater inflater = mode.getMenuInflater();
          inflater.inflate(R.menu.list_account, menu);
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
          accountsAdapter.setEditMode(false);
        }
      });
    }
  }

  @Override
  public void onEditMode(boolean isEditMode) {
    presenter.onEditModeList(isEditMode);
  }

  private void showEmptyView() {
    rvAccounts.setVisibility(View.GONE);
    tvEmptyView.setVisibility(View.VISIBLE);
  }

  private void showAccountList(List<AccountPasscode> accounts) {
    if (accountsAdapter == null) {
      accountsAdapter = new AccountsAdapter(getContext(), accounts);
      accountsAdapter.setOnEditListener(this);
      rvAccounts.setAdapter(accountsAdapter);
    } else {
      accountsAdapter.setAccounts(accounts);
      accountsAdapter.notifyDataSetChanged();
    }

    rvAccounts.setVisibility(View.VISIBLE);
    tvEmptyView.setVisibility(View.GONE);
  }

  private void stopCounters() {
    if (accountsAdapter == null) {
      return;
    }

    for (int i = 0; i < accountsAdapter.getItemCount(); i++) {
      AccountViewHolder viewHolder =
          (AccountViewHolder) rvAccounts.findViewHolderForAdapterPosition(i);
      if (viewHolder != null) {
        viewHolder.stopAnimation();
      }
    }
  }
}
