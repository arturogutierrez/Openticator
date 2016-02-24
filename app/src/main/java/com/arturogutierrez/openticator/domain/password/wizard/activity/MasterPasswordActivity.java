package com.arturogutierrez.openticator.domain.password.wizard.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import com.arturogutierrez.openticator.R;
import com.arturogutierrez.openticator.di.HasComponent;
import com.arturogutierrez.openticator.domain.password.wizard.di.DaggerMasterPasswordComponent;
import com.arturogutierrez.openticator.domain.password.wizard.di.MasterPasswordComponent;
import com.arturogutierrez.openticator.domain.password.wizard.di.MasterPasswordModule;
import com.arturogutierrez.openticator.domain.password.wizard.view.MasterPasswordFragment;
import com.arturogutierrez.openticator.view.activity.BaseActivity;

public class MasterPasswordActivity extends BaseActivity
    implements HasComponent<MasterPasswordComponent> {

  private MasterPasswordComponent masterPasswordComponent;

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
  public MasterPasswordComponent getComponent() {
    return masterPasswordComponent;
  }

  private void initializeActivity(Bundle savedInstanceState) {
    configureInjector();
    configureToolbar();

    if (savedInstanceState == null) {
      showMasterPasswordFragment();
    }
  }

  private void configureInjector() {
    masterPasswordComponent = DaggerMasterPasswordComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .masterPasswordModule(new MasterPasswordModule())
        .build();
  }

  private void configureToolbar() {
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setTitle(R.string.configure_master_password);
    }
  }

  private void showMasterPasswordFragment() {
    MasterPasswordFragment masterPasswordFragment = new MasterPasswordFragment();
    addFragment(R.id.content_frame, masterPasswordFragment);
  }
}
