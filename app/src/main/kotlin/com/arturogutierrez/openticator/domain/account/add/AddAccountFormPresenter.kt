package com.arturogutierrez.openticator.domain.account.add

import com.arturogutierrez.openticator.domain.account.AccountFactory
import com.arturogutierrez.openticator.domain.account.add.AddAccountView.InputError.EMPTY_ACCOUNT_NAME
import com.arturogutierrez.openticator.domain.account.add.AddAccountView.InputError.EMPTY_SECRET
import com.arturogutierrez.openticator.domain.account.add.AddAccountView.InputError.INVALID_SECRET
import com.arturogutierrez.openticator.domain.account.interactor.AddAccountInteractor
import com.arturogutierrez.openticator.domain.account.interactor.AddAccountInteractor.Params
import com.arturogutierrez.openticator.domain.account.model.Account
import com.arturogutierrez.openticator.helpers.isBase32
import com.arturogutierrez.openticator.interactor.DefaultSingleObserver
import com.arturogutierrez.openticator.view.presenter.Presenter
import javax.inject.Inject

class AddAccountFormPresenter @Inject constructor(private val accountFactory: AccountFactory,
                                                  private val addAccountInteractorInteractor: AddAccountInteractor) : Presenter<AddAccountView>() {

  override fun destroy() {
    addAccountInteractorInteractor.dispose()
  }

  fun createTimeBasedAccount(accountName: String?, accountSecret: String?) {
    if (accountName == null || accountName.trim().isEmpty()) {
      view?.showFieldError(EMPTY_ACCOUNT_NAME)
    } else if (accountSecret == null || accountSecret.trim().isEmpty()) {
      view?.showFieldError(EMPTY_SECRET)
    } else if (!accountSecret.isBase32()) {
      view?.showFieldError(INVALID_SECRET)
    } else {
      val newAccount = accountFactory.createAccount(accountName.trim(), accountSecret.trim())
      val params = Params(newAccount)
      addAccountInteractorInteractor.execute(params, object : DefaultSingleObserver<Account>() {
        override fun onSuccess(t: Account) {
          onAccountAdded()
        }

        override fun onError(e: Throwable) {
          onErrorAddingAccount()
        }
      })
    }
  }

  private fun onAccountAdded() {
    view?.dismissForm()
  }

  private fun onErrorAddingAccount() {
    view?.unableToAddAccount()
  }
}
