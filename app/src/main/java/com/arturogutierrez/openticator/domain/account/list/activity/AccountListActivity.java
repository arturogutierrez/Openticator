package com.arturogutierrez.openticator.domain.account.list.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import butterknife.Bind;
import butterknife.OnClick;
import com.arturogutierrez.openticator.R;
import com.arturogutierrez.openticator.di.HasComponent;
import com.arturogutierrez.openticator.domain.account.list.di.AccountListComponent;
import com.arturogutierrez.openticator.domain.account.list.di.AccountListModule;
import com.arturogutierrez.openticator.domain.account.list.di.DaggerAccountListComponent;
import com.arturogutierrez.openticator.domain.account.list.view.AccountListFragment;
import com.arturogutierrez.openticator.view.activity.BaseActivity;
import com.github.clans.fab.FloatingActionMenu;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.single.EmptyPermissionListener;
import com.karumi.dexter.listener.single.PermissionListener;

public class AccountListActivity extends BaseActivity
    implements HasComponent<AccountListComponent> {

  @Bind(R.id.drawerLayout)
  DrawerLayout drawerLayout;
  @Bind(R.id.fab_menu)
  FloatingActionMenu floatingActionMenu;
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

  @OnClick(R.id.fab_add_manually)
  public void onAddManuallyClicked() {
    floatingActionMenu.close(false);
    navigator.goToAddAccountManually(this);
  }

  @OnClick(R.id.fab_add_from_camera)
  public void onAddFromCamera() {
    checkCameraPermission();
  }

  private void initializeActivity(Bundle savedInstanceState) {
    configureInjector();
    showDrawerLayout();

    if (savedInstanceState == null) {
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

  private void checkCameraPermission() {
    PermissionListener permissionListener = new EmptyPermissionListener() {
      @Override
      public void onPermissionGranted(PermissionGrantedResponse response) {
        floatingActionMenu.close(false);
        navigator.goToAddAccountFromCamera(AccountListActivity.this);
      }

      @Override
      public void onPermissionDenied(PermissionDeniedResponse response) {
        showPermissionNotGranted();
      }
    };

    Dexter.checkPermission(permissionListener, Manifest.permission.CAMERA);
  }

  private void showPermissionNotGranted() {
    new AlertDialog.Builder(this).setTitle(R.string.camera_permission)
        .setMessage(R.string.camera_permission_needed_for_scan_qr)
        .setPositiveButton(android.R.string.ok, (dialog, which) -> {
          dialog.dismiss();
        })
        .show();
  }
}
