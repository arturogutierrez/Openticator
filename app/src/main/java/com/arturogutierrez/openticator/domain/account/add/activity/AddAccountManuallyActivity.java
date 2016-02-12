package com.arturogutierrez.openticator.domain.account.add.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import com.arturogutierrez.openticator.R;
import com.arturogutierrez.openticator.di.HasComponent;
import com.arturogutierrez.openticator.domain.account.add.di.AddAccountComponent;
import com.arturogutierrez.openticator.domain.account.add.di.AddAccountModule;
import com.arturogutierrez.openticator.domain.account.add.di.DaggerAddAccountComponent;
import com.arturogutierrez.openticator.domain.account.add.view.AddAccountFormFragment;
import com.arturogutierrez.openticator.view.activity.BaseActivity;

public class AddAccountManuallyActivity extends BaseActivity
    implements HasComponent<AddAccountComponent> {

  private AddAccountComponent addAccountComponent;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    initializeActivity(savedInstanceState);
  }

  @Override
  protected int getLayoutResource() {
    return R.layout.activity_toolbar;
  }

  @Override
  public AddAccountComponent getComponent() {
    return addAccountComponent;
  }

  private void initializeActivity(Bundle savedInstanceState) {
    if (savedInstanceState == null) {
      configureInjector();
      configureToolbar();
      showAddAccountFormFragment();
    }
  }

  private void configureInjector() {
    addAccountComponent = DaggerAddAccountComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .addAccountModule(new AddAccountModule())
        .build();
  }

  private void configureToolbar() {
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setTitle(R.string.add_account_manually);
      actionBar.setDisplayHomeAsUpEnabled(true);
    }
  }

  private void showAddAccountFormFragment() {
    AddAccountFormFragment addAccountFormFragment = new AddAccountFormFragment();
    addFragment(R.id.content_frame, addAccountFormFragment);
  }
}
