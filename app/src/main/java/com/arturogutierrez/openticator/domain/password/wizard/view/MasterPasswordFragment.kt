package com.arturogutierrez.openticator.domain.password.wizard.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import com.arturogutierrez.openticator.R
import com.arturogutierrez.openticator.domain.navigator.Navigator
import com.arturogutierrez.openticator.domain.password.wizard.MasterPasswordPresenter
import com.arturogutierrez.openticator.domain.password.wizard.di.MasterPasswordComponent
import com.arturogutierrez.openticator.view.fragment.BaseFragment
import org.jetbrains.anko.support.v4.find
import javax.inject.Inject

class MasterPasswordFragment : BaseFragment(), MasterPasswordView {

  @Inject
  internal lateinit var navigator: Navigator
  @Inject
  internal lateinit var presenter: MasterPasswordPresenter

  private val etPassword by lazy { find<TextView>(R.id.et_password) }
  private val etConfirmPassword by lazy { find<TextView>(R.id.et_confirm_password) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setHasOptionsMenu(true)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    initialize()
  }

  override val layoutResource: Int
    get() = R.layout.fragment_master_password

  override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
    super.onCreateOptionsMenu(menu, inflater)

    inflater!!.inflate(R.menu.master_password, menu)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item!!.itemId) {
      R.id.action_next -> {
        onNextClicked()
        return true
      }
      else -> return super.onOptionsItemSelected(item)
    }
  }

  private fun initialize() {
    initializeInjector()
    presenter.setView(this)
  }

  private fun initializeInjector() {
    getComponent(MasterPasswordComponent::class.java).inject(this)
  }

  private fun onNextClicked() {
    val password = etPassword.text.toString()
    val confirmPassword = etConfirmPassword.text.toString()
    presenter.createMasterPassword(password, confirmPassword)
  }

  private fun showError(message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
  }

  override fun showWeakPasswordError() {
    showError(getString(R.string.your_password_is_weak))
  }

  override fun showMismatchPasswordsError() {
    showError(getString(R.string.the_passwords_are_not_equal))
  }

  override fun closeWizard() {
    navigator.goToAccountList(context)
    val activity = activity
    activity?.finish()
  }
}
