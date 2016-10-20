package com.arturogutierrez.openticator.domain.navigator

import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import com.arturogutierrez.openticator.domain.account.add.activity.AddAccountFromCameraActivity
import com.arturogutierrez.openticator.domain.account.add.activity.AddAccountManuallyActivity
import com.arturogutierrez.openticator.domain.account.camera.activity.CaptureActivity
import com.arturogutierrez.openticator.domain.account.list.activity.AccountListActivity
import com.arturogutierrez.openticator.domain.password.wizard.activity.MasterPasswordActivity
import javax.inject.Inject

class Navigator @Inject constructor() {

  fun goToInitialWizard(context: Context) {
    val intent = Intent(context, MasterPasswordActivity::class.java)
    context.startActivity(intent)
  }

  fun goToAccountList(context: Context) {
    val intent = Intent(context, AccountListActivity::class.java)
    context.startActivity(intent)
  }

  fun goToAddAccountManually(context: Context) {
    val intent = Intent(context, AddAccountManuallyActivity::class.java)
    context.startActivity(intent)
  }

  fun goToAddAccountFromCamera(context: Context) {
    val intent = Intent(context, AddAccountFromCameraActivity::class.java)
    context.startActivity(intent)
  }

  fun goToCaptureCode(context: Context, fragment: Fragment, requestCode: Int) {
    val intent = Intent(context, CaptureActivity::class.java)
    fragment.startActivityForResult(intent, requestCode)
  }
}
