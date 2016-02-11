package com.arturogutierrez.openticator.domain.account.list.activity;

import android.os.Bundle;
import com.arturogutierrez.openticator.R;
import com.arturogutierrez.openticator.di.HasComponent;
import com.arturogutierrez.openticator.domain.account.list.di.AccountListComponent;
import com.arturogutierrez.openticator.domain.account.list.di.AccountListModule;
import com.arturogutierrez.openticator.domain.account.list.di.DaggerAccountListComponent;
import com.arturogutierrez.openticator.view.activity.BaseActivity;
import com.arturogutierrez.openticator.domain.account.list.fragment.AccountListFragment;

public class AccountListActivity extends BaseActivity implements HasComponent<AccountListComponent> {

  private AccountListComponent accountListComponent;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    initializeActivity(savedInstanceState);
  }

  @Override
  protected int getLayoutResource() {
    return R.layout.activity_account_list;
  }

  @Override
  public AccountListComponent getComponent() {
    return accountListComponent;
  }

  private void initializeActivity(Bundle savedInstanceState) {
    if (savedInstanceState == null) {
      configureInjector();
      showAccountListFragment();
    }
  }

  private void configureInjector() {
    accountListComponent = DaggerAccountListComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .accountListModule(new AccountListModule())
        .build();
  }

  private void showAccountListFragment() {
    AccountListFragment accountListFragment = new AccountListFragment();
    addFragment(R.id.content_frame, accountListFragment);
  }
}
