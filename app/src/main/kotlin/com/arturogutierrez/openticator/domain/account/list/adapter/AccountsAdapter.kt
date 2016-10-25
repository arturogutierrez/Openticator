package com.arturogutierrez.openticator.domain.account.list.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.arturogutierrez.openticator.R
import com.arturogutierrez.openticator.domain.account.model.Account
import com.arturogutierrez.openticator.domain.account.model.AccountPasscode
import rx.Observable
import rx.subjects.PublishSubject
import java.util.*

class AccountsAdapter(val layoutInflater: LayoutInflater) : RecyclerView.Adapter<AccountViewHolder>() {

  private var editMode: Boolean = false
  private val editModeSubject: PublishSubject<Boolean>
  private val selectedAccountsSubject: PublishSubject<Set<Account>>

  var accounts = emptyList<AccountPasscode>()
  val selectedAccounts: MutableSet<Account>
  var onSelectedAccount: ((AccountPasscode) -> Unit)? = null

  init {
    this.selectedAccounts = HashSet<Account>(accounts.size)
    this.editMode = false
    this.editModeSubject = PublishSubject.create<Boolean>()
    this.selectedAccountsSubject = PublishSubject.create<Set<Account>>()
  }

  fun editMode(): Observable<Boolean> {
    return editModeSubject
  }

  fun selectedAccounts(): Observable<Set<Account>> {
    return selectedAccountsSubject
  }

  fun setEditMode(editMode: Boolean) {
    if (this.editMode == editMode) {
      return
    }

    this.editMode = editMode
    if (!editMode) {
      clearSelection()
    }

    editModeSubject.onNext(editMode)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
    val view = layoutInflater.inflate(R.layout.layout_account_row, parent, false)
    return AccountViewHolder(view, { onItemClick(it) }, { onLongItemClick(it) })
  }

  override fun onBindViewHolder(viewHolder: AccountViewHolder, position: Int) {
    val accountPasscode = accounts[position]
    val isSelected = selectedAccounts.contains(accountPasscode.account)
    viewHolder.showAccount(accountPasscode, isSelected)
  }

  override fun getItemCount(): Int {
    return accounts.size
  }

  private fun clearSelection() {
    if (selectedAccounts.size > 0) {
      selectedAccounts.clear()
      notifyDataSetChanged()
    }
  }

  private fun onItemClick(position: Int) {
    if (editMode) {
      selectOrDeselect(position)
      return
    }

    onSelectedAccount?.invoke(accounts[position])
  }

  private fun onLongItemClick(position: Int): Boolean {
    if (!editMode) {
      setEditMode(true)
      selectOrDeselect(position)
    }

    return true
  }

  private fun selectOrDeselect(position: Int) {
    val accountPasscode = accounts[position]
    val account = accountPasscode.account
    if (selectedAccounts.contains(account)) {
      selectedAccounts.remove(account)
    } else {
      selectedAccounts.add(account)
    }
    notifyItemChanged(position)

    selectedAccountsSubject.onNext(selectedAccounts)
  }
}
