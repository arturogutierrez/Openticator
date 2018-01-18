package com.arturogutierrez.openticator.domain.account.list.view

import android.app.Activity
import android.support.v7.view.ActionMode
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.afollestad.materialdialogs.MaterialDialog
import com.arturogutierrez.openticator.R
import com.arturogutierrez.openticator.domain.account.list.AccountEditModePresenter
import com.arturogutierrez.openticator.domain.account.list.AccountEditModeView
import com.arturogutierrez.openticator.domain.account.list.adapter.AccountsAdapter
import com.arturogutierrez.openticator.domain.account.list.adapter.IssuersAdapter
import com.arturogutierrez.openticator.domain.account.list.di.AccountListComponent
import com.arturogutierrez.openticator.domain.account.model.Account
import com.arturogutierrez.openticator.domain.category.model.Category
import com.arturogutierrez.openticator.domain.issuer.IssuerDecorator
import com.arturogutierrez.openticator.helpers.consume
import rx.Subscription
import javax.inject.Inject

class AccountEditActionMode(accountListComponent: AccountListComponent,
    val accountsAdapter: AccountsAdapter) : ActionMode.Callback, AccountEditModeView {

  @Inject
  internal lateinit var activity: Activity
  @Inject
  internal lateinit var presenter: AccountEditModePresenter
  @Inject
  internal lateinit var layoutInflater: LayoutInflater

  private val accountsSubscription: Subscription
  private var actionMode: ActionMode? = null
  private var menuItemDelete: MenuItem? = null
  private var menuItemCategory: MenuItem? = null
  private var menuItemEdit: MenuItem? = null
  private var menuItemLogo: MenuItem? = null

  init {
    initializeInjector(accountListComponent)
    accountsSubscription = accountsAdapter.selectedAccounts().subscribe { onSelectedAccounts(it) }
  }

  private fun initializeInjector(accountListComponent: AccountListComponent) {
    accountListComponent.inject(this)
  }

  override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
    configureActionMenu(mode.menuInflater, menu)
    presenter.attachView(this)
    actionMode = mode

    return true
  }

  override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
    menuItemDelete?.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
    menuItemEdit?.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
    menuItemCategory?.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
    menuItemLogo?.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
    return true
  }

  override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.action_delete -> consume { deleteSelectedAccounts() }
      R.id.action_edit -> consume { changeNameForSelectedAccount() }
      R.id.action_category -> consume { changeCategoryForSelectedAccount() }
      R.id.action_logo -> consume { changeLogoForSelectedAccount() }
      else -> false
    }
  }

  override fun onDestroyActionMode(mode: ActionMode) {
    menuItemEdit = null
    accountsAdapter.editMode = false
    presenter.detachView()
    accountsSubscription.unsubscribe()
  }

  private fun configureActionMenu(inflater: MenuInflater, menu: Menu) {
    inflater.inflate(R.menu.account_edit_action_mode, menu)
    menuItemDelete = menu.findItem(R.id.action_delete)
    menuItemEdit = menu.findItem(R.id.action_edit)
    menuItemCategory = menu.findItem(R.id.action_category)
    menuItemLogo = menu.findItem(R.id.action_logo)
  }

  private fun onSelectedAccounts(selectedAccounts: List<Account>) {
    presenter.onSelectedAccounts(selectedAccounts)
  }

  private fun deleteSelectedAccounts() {
    presenter.deleteAccounts(accountsAdapter.selectedAccounts)
  }

  private fun changeNameForSelectedAccount() {
    val selectedAccount = accountsAdapter.selectedAccounts.iterator().next()

    MaterialDialog.Builder(activity)
        .title(R.string.rename_account)
        .input(null, selectedAccount.name) { _, input ->
          presenter.updateAccount(selectedAccount, input.toString())
        }.show()
  }

  private fun changeCategoryForSelectedAccount() {
    val selectedAccount = accountsAdapter.selectedAccounts.iterator().next()
    presenter.pickCategoryForAccount(selectedAccount)
  }

  private fun changeLogoForSelectedAccount() {
    val selectedAccount = accountsAdapter.selectedAccounts.iterator().next()
    presenter.pickLogoForAccount(selectedAccount)
  }

  override fun dismissActionMode() {
    actionMode?.finish()
  }

  override fun showCategoryButton(isVisible: Boolean) {
    menuItemCategory?.isVisible = isVisible
  }

  override fun showEditButton(isVisible: Boolean) {
    menuItemEdit?.isVisible = isVisible
  }

  override fun showLogoButton(isVisible: Boolean) {
    menuItemLogo?.isVisible = isVisible
  }

  override fun showChooseEmptyCategory(account: Account) {
    MaterialDialog.Builder(activity).title(R.string.add_to_category).content(R.string.no_categories).positiveText(
        R.string.create_new_category).onPositive { dialog, _ ->
      dialog.dismiss()
      showAddNewCategory(account)
    }.show()
  }

  private fun showAddNewCategory(account: Account) {
    MaterialDialog.Builder(activity).title(R.string.add_to_category).input(R.string.category, 0) { dialog, input ->
      dialog.dismiss()
      presenter.createCategory(input.toString(), account)
    }.show()
  }

  override fun showChooseCategory(categories: List<Category>, account: Account) {
    val stringCategories = categories.map { it.name }

    MaterialDialog.Builder(activity).title(R.string.add_to_category).items(stringCategories).itemsCallback { _, _, which, _ ->
      val category = categories[which]
      presenter.addAccountToCategory(category, account)
    }.positiveText(R.string.create_new_category).onPositive { dialog, _ ->
      dialog.dismiss()
      showAddNewCategory(account)
    }.show()
  }

  override fun showChooseLogo(issuers: List<IssuerDecorator>, account: Account) {
    val logosView = layoutInflater.inflate(R.layout.dialog_issuers, null)
    val gridLayoutManager = GridLayoutManager(activity, 3)
    val recyclerView = logosView.findViewById<RecyclerView>(R.id.rv_logos)
    recyclerView.layoutManager = gridLayoutManager

    val dialog = MaterialDialog.Builder(activity)
        .title(R.string.choose_logo)
        .customView(logosView, false)
        .negativeText(android.R.string.cancel)
        .show()

    recyclerView.adapter = IssuersAdapter(layoutInflater, issuers) { issuer ->
      presenter.updateAccount(account, issuer)
      dialog.dismiss()
    }
  }
}
