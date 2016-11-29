package com.arturogutierrez.openticator.domain.account.add

import com.arturogutierrez.openticator.domain.account.add.AddAccountView.InputError.*
import com.arturogutierrez.openticator.domain.account.interactor.AddAccountInteractor
import com.arturogutierrez.openticator.domain.account.model.Account
import com.arturogutierrez.openticator.helpers.isBase32
import com.arturogutierrez.openticator.interactor.DefaultSubscriber
import com.arturogutierrez.openticator.view.presenter.Presenter
import javax.inject.Inject

class AddAccountFormPresenter @Inject constructor(val addAccountInteractorInteractor: AddAccountInteractor) : Presenter<AddAccountView>() {

  override fun destroy() {
    addAccountInteractorInteractor.unsubscribe()
  }

  fun createTimeBasedAccount(accountName: String?, accountSecret: String?) {
    if (accountName == null || accountName.trim().isEmpty()) {
      view?.showFieldError(EMPTY_ACCOUNT_NAME)
    } else if (accountSecret == null || accountSecret.trim().isEmpty()) {
      view?.showFieldError(EMPTY_SECRET)
    } else if (!accountSecret.isBase32()) {
      view?.showFieldError(INVALID_SECRET)
    } else {
      addAccountInteractorInteractor.configure(accountName.trim(), accountSecret.trim())
      addAccountInteractorInteractor.execute(object : DefaultSubscriber<Account>() {
        override fun onNext(item: Account) {
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
