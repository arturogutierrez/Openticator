package com.arturogutierrez.openticator.domain.account.list

import com.arturogutierrez.openticator.domain.account.model.AccountPasscode

interface AccountListView {

  fun viewNoItems()

  fun renderAccounts(accounts: List<AccountPasscode>)

  fun startEditMode()
}
