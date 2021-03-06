package com.arturogutierrez.openticator.domain.account.list.view

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.arturogutierrez.openticator.R
import com.arturogutierrez.openticator.domain.account.list.AccountListPresenter
import com.arturogutierrez.openticator.domain.account.list.AccountListView
import com.arturogutierrez.openticator.domain.account.list.adapter.AccountViewHolder
import com.arturogutierrez.openticator.domain.account.list.adapter.AccountsAdapter
import com.arturogutierrez.openticator.domain.account.list.di.AccountListComponent
import com.arturogutierrez.openticator.domain.account.model.AccountPasscode
import com.arturogutierrez.openticator.helpers.disableAppBarLayoutScroll
import com.arturogutierrez.openticator.helpers.enableAppBarLayoutScroll
import com.arturogutierrez.openticator.helpers.find
import com.arturogutierrez.openticator.view.fragment.BaseFragment
import com.arturogutierrez.openticator.view.fragment.makeSnackbar
import com.arturogutierrez.openticator.view.widget.gone
import com.arturogutierrez.openticator.view.widget.visible
import javax.inject.Inject

class AccountListFragment : BaseFragment(), AccountListView {

  @Inject
  internal lateinit var presenter: AccountListPresenter

  private val rvAccounts by lazy { find<RecyclerView>(R.id.rv_accounts) }
  private val tvEmptyView by lazy { find<TextView>(R.id.tv_empty_view) }
  private val accountsAdapter by lazy { AccountsAdapter(activity!!.layoutInflater) }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    initialize()
  }

  override fun onResume() {
    super.onResume()
    presenter.attachView(this)
    presenter.resume()
  }

  override fun onPause() {
    super.onPause()
    presenter.detachView()
    presenter.pause()
    stopCounters()
  }

  override fun onDestroy() {
    super.onDestroy()
    presenter.destroy()
  }

  override val layoutResource: Int
    get() = R.layout.fragment_account_list

  override fun configureUI(view: View) {
    accountsAdapter.editModeObservable().subscribe { presenter.onEditModeList(it) }
    accountsAdapter.onSelectedAccountPasscode = { presenter.onPasscodeSelected(it) }

    val linearLayoutManager = LinearLayoutManager(context)
    linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
    rvAccounts.layoutManager = linearLayoutManager
    rvAccounts.adapter = accountsAdapter
  }

  private fun initialize() {
    initializeInjector()
  }

  private fun initializeInjector() {
    component.inject(this)
  }

  private val component: AccountListComponent
    get() = getComponent(AccountListComponent::class.java)

  override fun noItems() {
    updateAccounts(emptyList())
    showEmptyView()
  }

  override fun renderAccounts(accounts: List<AccountPasscode>) {
    showAccountList(accounts)
  }

  override fun startEditMode() {
    val activity = activity as AppCompatActivity
    val accountEditActionMode = AccountEditActionMode(accountsAdapter, component)
    activity.startSupportActionMode(accountEditActionMode)
  }

  override fun passcodeCopiedToClipboard() {
    makeSnackbar(R.string.passcode_copied_to_clipboard, Snackbar.LENGTH_LONG)
  }

  private fun showEmptyView() {
    rvAccounts.gone()
    tvEmptyView.visible()
  }

  private fun showAccountList(accounts: List<AccountPasscode>) {
    updateAccounts(accounts)
    updateScrollBasedOnContent(accounts)

    rvAccounts.visible()
    tvEmptyView.gone()
  }

  private fun stopCounters() {
    (0 until accountsAdapter.itemCount)
        .map { rvAccounts.findViewHolderForAdapterPosition(it) as? AccountViewHolder }
        .forEach { it?.stopAnimation() }
  }

  private fun updateAccounts(accounts: List<AccountPasscode>) {
    accountsAdapter.accounts = accounts
  }

  private fun updateScrollBasedOnContent(accounts: List<AccountPasscode>) {
    rvAccounts.post {
      val layoutManager = rvAccounts.layoutManager as LinearLayoutManager
      if (layoutManager.findLastCompletelyVisibleItemPosition() == accounts.size - 1) {
        activity?.disableAppBarLayoutScroll()
      } else {
        activity?.enableAppBarLayoutScroll()
      }
    }
  }
}
