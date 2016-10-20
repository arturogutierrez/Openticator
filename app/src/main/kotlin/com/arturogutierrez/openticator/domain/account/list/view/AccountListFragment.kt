package com.arturogutierrez.openticator.domain.account.list.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.arturogutierrez.openticator.R
import com.arturogutierrez.openticator.domain.account.list.AccountListPresenter
import com.arturogutierrez.openticator.domain.account.list.AccountListView
import com.arturogutierrez.openticator.domain.account.list.adapter.AccountViewHolder
import com.arturogutierrez.openticator.domain.account.list.adapter.AccountsAdapter
import com.arturogutierrez.openticator.domain.account.list.di.AccountListComponent
import com.arturogutierrez.openticator.domain.account.model.AccountPasscode
import com.arturogutierrez.openticator.view.fragment.BaseFragment
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.find
import javax.inject.Inject

class AccountListFragment : BaseFragment(), AccountListView {

  @Inject
  internal lateinit var presenter: AccountListPresenter
  @Inject
  internal lateinit var layoutInflater: LayoutInflater

  private lateinit var rvAccounts: RecyclerView
  private lateinit var tvEmptyView: TextView

  private var accountsAdapter: AccountsAdapter? = null

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    initialize()
  }

  override fun onResume() {
    super.onResume()
    presenter.resume()
  }

  override fun onPause() {
    super.onPause()
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
    tvEmptyView = view.find(R.id.tv_empty_view)
    rvAccounts = view.find(R.id.rv_accounts)

    val linearLayoutManager = LinearLayoutManager(context)
    linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
    rvAccounts.layoutManager = linearLayoutManager
  }

  private fun initialize() {
    initializeInjector()
    presenter.setView(this)
  }

  private fun initializeInjector() {
    component.inject(this)
  }

  private val component: AccountListComponent
    get() = getComponent(AccountListComponent::class.java)

  override fun viewNoItems() {
    showEmptyView()
  }

  override fun renderAccounts(accounts: List<AccountPasscode>) {
    showAccountList(accounts)
  }

  override fun startEditMode() {
    val activity = activity as AppCompatActivity
    val accountEditActionMode = AccountEditActionMode(component, accountsAdapter!!)
    activity.startSupportActionMode(accountEditActionMode)
  }

  private fun showEmptyView() {
    rvAccounts.visibility = View.GONE
    tvEmptyView.visibility = View.VISIBLE
  }

  private fun showAccountList(accounts: List<AccountPasscode>) {
    if (accountsAdapter == null) {
      accountsAdapter = AccountsAdapter(layoutInflater, accounts)
      accountsAdapter?.let {
        it.editMode().subscribe { presenter.onEditModeList(it) }
        rvAccounts.adapter = accountsAdapter
      }
    } else {
      accountsAdapter?.let {
        it.accounts = accounts
        it.notifyDataSetChanged()
      }
    }

    rvAccounts.visibility = View.VISIBLE
    tvEmptyView.visibility = View.GONE
  }

  private fun stopCounters() {
    val accountsAdapter = accountsAdapter ?: return

    for (i in 0..accountsAdapter.itemCount - 1) {
      val viewHolder = rvAccounts.findViewHolderForAdapterPosition(i) as AccountViewHolder
      viewHolder.stopAnimation()
    }
  }
}
