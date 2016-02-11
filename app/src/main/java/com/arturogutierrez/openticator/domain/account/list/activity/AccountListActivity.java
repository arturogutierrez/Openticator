package com.arturogutierrez.openticator.domain.account.list.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import butterknife.Bind;
import com.arturogutierrez.openticator.R;
import com.arturogutierrez.openticator.di.HasComponent;
import com.arturogutierrez.openticator.domain.account.list.di.AccountListComponent;
import com.arturogutierrez.openticator.domain.account.list.di.AccountListModule;
import com.arturogutierrez.openticator.domain.account.list.di.DaggerAccountListComponent;
import com.arturogutierrez.openticator.domain.account.list.fragment.AccountListFragment;
import com.arturogutierrez.openticator.view.activity.BaseActivity;

public class AccountListActivity extends BaseActivity
    implements HasComponent<AccountListComponent> {

  @Bind(R.id.drawerLayout)
  DrawerLayout drawerLayout;
  ActionBarDrawerToggle drawerToggle;

  private AccountListComponent accountListComponent;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    initializeActivity(savedInstanceState);
  }

  @Override
  protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);

    if (drawerToggle != null) {
      drawerToggle.syncState();
    }
  }

  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);

    if (drawerToggle != null) {
      drawerToggle.onConfigurationChanged(newConfig);
    }
  }

  @Override
  protected int getLayoutResource() {
    return R.layout.activity_toolbar_drawer;
  }

  @Override
  public AccountListComponent getComponent() {
    return accountListComponent;
  }

  private void initializeActivity(Bundle savedInstanceState) {
    if (savedInstanceState == null) {
      configureInjector();
      showDrawerLayout();
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

  private void showDrawerLayout() {
    if (drawerLayout != null) {
      drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open,
          R.string.drawer_close);
      drawerLayout.setDrawerListener(drawerToggle);
    }
  }

  private void showAccountListFragment() {
    AccountListFragment accountListFragment = new AccountListFragment();
    addFragment(R.id.content_frame, accountListFragment);
  }
}
