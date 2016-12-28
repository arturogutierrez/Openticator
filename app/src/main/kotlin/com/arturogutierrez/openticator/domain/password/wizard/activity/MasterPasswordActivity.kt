package com.arturogutierrez.openticator.domain.password.wizard.activity

import android.os.Bundle
import com.arturogutierrez.openticator.R
import com.arturogutierrez.openticator.di.HasComponent
import com.arturogutierrez.openticator.domain.password.wizard.di.DaggerMasterPasswordComponent
import com.arturogutierrez.openticator.domain.password.wizard.di.MasterPasswordComponent
import com.arturogutierrez.openticator.domain.password.wizard.di.MasterPasswordModule
import com.arturogutierrez.openticator.domain.password.wizard.view.MasterPasswordFragment
import com.arturogutierrez.openticator.view.activity.BaseActivity

class MasterPasswordActivity : BaseActivity(), HasComponent<MasterPasswordComponent> {

  override val component by lazy { buildComponent() }

  override val layoutResource: Int
    get() = R.layout.activity_toolbar

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    initializeActivity(savedInstanceState)
  }

  private fun initializeActivity(savedInstanceState: Bundle?) {
    configureToolbar()

    if (savedInstanceState == null) {
      showMasterPasswordFragment()
    }
  }

  private fun buildComponent(): MasterPasswordComponent {
    return DaggerMasterPasswordComponent.builder()
        .applicationComponent(applicationComponent)
        .activityModule(activityModule)
        .masterPasswordModule(MasterPasswordModule())
        .build()
  }

  private fun configureToolbar() {
    with(supportActionBar) {
      setTitle(R.string.configure_master_password)
    }
  }

  private fun showMasterPasswordFragment() {
    val masterPasswordFragment = MasterPasswordFragment()
    replaceFragment(R.id.content_frame, masterPasswordFragment)
  }
}
