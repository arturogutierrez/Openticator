package com.arturogutierrez.openticator.domain.navigator;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import com.arturogutierrez.openticator.domain.account.add.activity.AddAccountFromCameraActivity;
import com.arturogutierrez.openticator.domain.account.add.activity.AddAccountManuallyActivity;
import com.arturogutierrez.openticator.domain.account.camera.activity.CaptureActivity;
import com.arturogutierrez.openticator.domain.account.list.activity.AccountListActivity;
import com.arturogutierrez.openticator.domain.password.wizard.activity.MasterPasswordActivity;
import javax.inject.Inject;

public class Navigator {

  @Inject
  public Navigator() {

  }

  public void goToInitialWizard(Context context) {
    Intent intent = new Intent(context, MasterPasswordActivity.class);
    context.startActivity(intent);
  }

  public void goToAccountList(Context context) {
    Intent intent = new Intent(context, AccountListActivity.class);
    context.startActivity(intent);
  }

  public void goToAddAccountManually(Context context) {
    Intent intent = new Intent(context, AddAccountManuallyActivity.class);
    context.startActivity(intent);
  }

  public void goToAddAccountFromCamera(Context context) {
    Intent intent = new Intent(context, AddAccountFromCameraActivity.class);
    context.startActivity(intent);
  }

  public void goToCaptureCode(Context context, Fragment fragment, int requestCode) {
    Intent intent = new Intent(context, CaptureActivity.class);
    fragment.startActivityForResult(intent, requestCode);
  }
}
