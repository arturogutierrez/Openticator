package com.arturogutierrez.openticator.domain.account.list.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.arturogutierrez.openticator.R
import com.arturogutierrez.openticator.domain.account.model.Account
import com.arturogutierrez.openticator.domain.account.model.AccountPasscode
import io.reactivex.Flowable
import io.reactivex.processors.PublishProcessor
import kotlin.properties.Delegates

class AccountsAdapter(private val layoutInflater: LayoutInflater) : RecyclerView.Adapter<AccountViewHolder>() {

  private val selectedAccountsSubject = PublishProcessor.create<List<Account>>()
  private val editModeSubject = PublishProcessor.create<Boolean>()

  var editMode by Delegates.observable(false) { _, _, newEditMode ->
    if (!newEditMode) {
      clearSelection()
    }
    editModeSubject.onNext(newEditMode)
  }
  var accounts by Delegates.observable(emptyList<AccountPasscode>()) { _, _, _ ->
    notifyDataSetChanged()
  }
  var selectedAccounts by Delegates.observable(emptyList<Account>()) { _, _, new ->
    selectedAccountsSubject.onNext(new)
  }
  var onSelectedAccountPasscode: ((AccountPasscode) -> Unit) = { }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
    val view = layoutInflater.inflate(R.layout.layout_account_row, parent, false)
    return AccountViewHolder(view, { onItemClick(it) }, { onLongItemClick(it) })
  }

  override fun onBindViewHolder(viewHolder: AccountViewHolder, position: Int) {
    val accountPasscode = accounts[position]
    val isSelected = selectedAccounts.contains(accountPasscode.account)
    viewHolder.showAccount(accountPasscode, isSelected)
  }

  override fun getItemCount() = accounts.size

  fun editModeObservable(): Flowable<Boolean> = editModeSubject

  fun selectedAccounts(): Flowable<List<Account>> = selectedAccountsSubject

  private fun clearSelection() {
    if (selectedAccounts.isNotEmpty()) {
      selectedAccounts = emptyList()
      notifyDataSetChanged()
    }
  }

  private fun onItemClick(position: Int) {
    if (editMode) {
      selectOrDeselect(position)
      return
    }

    onSelectedAccountPasscode.invoke(accounts[position])
  }

  private fun onLongItemClick(position: Int): Boolean {
    if (!editMode) {
      editMode = true
      selectOrDeselect(position)
    }

    return true
  }

  private fun selectOrDeselect(position: Int) {
    val accountPasscode = accounts[position]
    val account = accountPasscode.account
    selectedAccounts = if (selectedAccounts.contains(account)) {
      selectedAccounts.filter { it != account }
    } else {
      selectedAccounts.plus(account)
    }
    notifyItemChanged(position)
  }
}
