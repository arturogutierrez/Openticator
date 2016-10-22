package com.arturogutierrez.openticator.domain.account.list

import com.arturogutierrez.openticator.domain.account.model.AccountPasscode
import com.arturogutierrez.openticator.view.presenter.Presenter

interface AccountListView : Presenter.View {

  fun noItems()

  fun renderAccounts(accounts: List<AccountPasscode>)

  fun startEditMode()
}
