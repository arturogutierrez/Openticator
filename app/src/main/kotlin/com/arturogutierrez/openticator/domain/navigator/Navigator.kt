package com.arturogutierrez.openticator.domain.navigator

import android.content.Context
import android.support.v4.app.Fragment
import com.arturogutierrez.openticator.domain.account.add.activity.AddAccountFromCameraActivity
import com.arturogutierrez.openticator.domain.account.add.activity.AddAccountManuallyActivity
import com.arturogutierrez.openticator.domain.account.camera.activity.CaptureActivity
import com.arturogutierrez.openticator.domain.account.list.activity.AccountListActivity
import com.arturogutierrez.openticator.domain.password.wizard.activity.MasterPasswordActivity
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity
import javax.inject.Inject

class Navigator @Inject constructor() {

  fun goToInitialWizard(context: Context) {
    context.startActivity<MasterPasswordActivity>()
  }

  fun goToAccountList(context: Context) {
    context.startActivity<AccountListActivity>()
  }

  fun goToAddAccountManually(context: Context) {
    context.startActivity<AddAccountManuallyActivity>()
  }

  fun goToAddAccountFromCamera(context: Context) {
    context.startActivity<AddAccountFromCameraActivity>()
  }

  fun goToCaptureCode(context: Context, fragment: Fragment, requestCode: Int) {
    val intent = context.intentFor<CaptureActivity>()
    fragment.startActivityForResult(intent, requestCode)
  }
}
