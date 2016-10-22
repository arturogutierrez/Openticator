package com.arturogutierrez.openticator.domain.account.add.view

import android.os.Bundle
import android.support.v7.widget.AppCompatEditText
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import com.arturogutierrez.openticator.R
import com.arturogutierrez.openticator.domain.account.add.AddAccountFormPresenter
import com.arturogutierrez.openticator.domain.account.add.AddAccountView
import com.arturogutierrez.openticator.domain.account.add.di.AddAccountComponent
import com.arturogutierrez.openticator.view.fragment.BaseFragment
import org.jetbrains.anko.support.v4.find
import javax.inject.Inject

class AddAccountFormFragment : BaseFragment(), AddAccountView {

  @Inject
  internal lateinit var presenter: AddAccountFormPresenter

  private val etAccountName by lazy { find<AppCompatEditText>(R.id.et_account_name) }
  private val etAccountSecret by lazy { find<AppCompatEditText>(R.id.et_account_secret) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setHasOptionsMenu(true)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    initialize()
  }

  override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
    super.onCreateOptionsMenu(menu, inflater)

    inflater?.inflate(R.menu.account_add, menu)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      R.id.action_add -> {
        onAddAccountClicked()
        return true
      }
      else -> return super.onOptionsItemSelected(item)
    }
  }

  override fun onResume() {
    super.onResume()
    presenter.resume()
  }

  override fun onPause() {
    super.onPause()
    presenter.pause()
  }

  override fun onDestroy() {
    super.onDestroy()
    presenter.destroy()
  }

  override val layoutResource: Int
    get() = R.layout.fragment_account_add_manually

  private fun initialize() {
    initializeInjector()
    presenter.view = this
  }

  private fun initializeInjector() {
    getComponent(AddAccountComponent::class.java).inject(this)
  }

  private fun onAddAccountClicked() {
    val accountName = etAccountName.text.toString()
    val accountSecret = etAccountSecret.text.toString()

    presenter.createTimeBasedAccount(accountName, accountSecret)
  }

  override fun dismissForm() {
    val activity = activity
    activity?.finish()
  }

  override fun showFieldError(fieldError: AddAccountView.FieldError) {
    val message = when (fieldError) {
      AddAccountView.FieldError.NAME -> getString(R.string.please_set_account_name)
      AddAccountView.FieldError.SECRET -> getString(R.string.please_set_account_secret)
    }

    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
  }

  override fun unableToAddAccount() {
    Toast.makeText(context, getString(R.string.unable_to_save_the_account), Toast.LENGTH_SHORT).show()
    // TODO: Detect error and show a more friendly message
  }
}
