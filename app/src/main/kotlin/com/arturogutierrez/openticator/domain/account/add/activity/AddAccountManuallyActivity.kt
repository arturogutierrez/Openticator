package com.arturogutierrez.openticator.domain.account.add.activity

import android.os.Bundle
import com.arturogutierrez.openticator.R
import com.arturogutierrez.openticator.di.HasComponent
import com.arturogutierrez.openticator.domain.account.add.di.AddAccountComponent
import com.arturogutierrez.openticator.domain.account.add.di.AddAccountModule
import com.arturogutierrez.openticator.domain.account.add.di.DaggerAddAccountComponent
import com.arturogutierrez.openticator.domain.account.add.view.AddAccountFormFragment
import com.arturogutierrez.openticator.view.activity.BaseActivity

class AddAccountManuallyActivity : BaseActivity(), HasComponent<AddAccountComponent> {

  override val component by lazy { buildComponent() }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    initializeActivity(savedInstanceState)
  }

  override val layoutResource: Int
    get() = R.layout.activity_toolbar

  private fun initializeActivity(savedInstanceState: Bundle?) {
    configureToolbar()

    if (savedInstanceState == null) {
      showAddAccountFormFragment()
    }
  }

  private fun buildComponent(): AddAccountComponent {
    return DaggerAddAccountComponent.builder()
        .applicationComponent(applicationComponent)
        .activityModule(activityModule)
        .addAccountModule(AddAccountModule())
        .build()
  }

  private fun configureToolbar() {
    val actionBar = supportActionBar ?: return

    with(actionBar) {
      setTitle(R.string.add_account_manually)
      setDisplayHomeAsUpEnabled(true)
    }
  }

  private fun showAddAccountFormFragment() {
    val addAccountFormFragment = AddAccountFormFragment()
    replaceFragment(R.id.content_frame, addAccountFormFragment)
  }
}
