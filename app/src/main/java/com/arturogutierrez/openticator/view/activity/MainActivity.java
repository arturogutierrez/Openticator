package com.arturogutierrez.openticator.view.activity;

import android.os.Bundle;
import com.arturogutierrez.openticator.R;
import com.arturogutierrez.openticator.di.HasComponent;
import com.arturogutierrez.openticator.di.component.AccountListComponent;
import com.arturogutierrez.openticator.di.component.DaggerAccountListComponent;
import com.arturogutierrez.openticator.di.module.AccountListModule;
import com.arturogutierrez.openticator.view.fragment.AccountListFragment;

public class MainActivity extends BaseActivity implements HasComponent<AccountListComponent> {

  private AccountListComponent accountListComponent;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    initializeActivity(savedInstanceState);
  }

  @Override
  protected int getLayoutResource() {
    return R.layout.activity_main;
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
