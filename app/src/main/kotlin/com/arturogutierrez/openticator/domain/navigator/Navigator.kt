package com.arturogutierrez.openticator.domain.navigator

import android.content.Context
import android.support.v4.app.Fragment
import com.arturogutierrez.openticator.domain.account.add.activity.AddAccountFromCameraActivity
import com.arturogutierrez.openticator.domain.account.add.activity.AddAccountManuallyActivity
import com.arturogutierrez.openticator.domain.account.list.activity.AccountListActivity
import com.arturogutierrez.openticator.domain.password.wizard.activity.MasterPasswordActivity
import com.arturogutierrez.openticator.domain.settings.activity.SettingsActivity
import com.arturogutierrez.openticator.domain.welcome.activity.WelcomeActivity
import com.arturogutierrez.openticator.helpers.startActivity
import com.google.zxing.integration.android.IntentIntegrator
import javax.inject.Inject

class Navigator @Inject constructor() {

  fun goToWelcomeScreen(context: Context) {
    context.startActivity<WelcomeActivity>()
  }

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

  fun goToCaptureCode(fragment: Fragment) {
    val intentIntegrator = IntentIntegrator.forSupportFragment(fragment)
    intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES)
    intentIntegrator.setOrientationLocked(false)
    intentIntegrator.setBeepEnabled(true)
    intentIntegrator.initiateScan()
  }

  fun goToSettings(context: Context) {
    context.startActivity<SettingsActivity>()
  }
}
