package com.arturogutierrez.openticator.domain.account.add

import com.arturogutierrez.openticator.domain.account.interactor.AddAccountInteractor
import com.arturogutierrez.openticator.domain.account.model.Account
import com.arturogutierrez.openticator.interactor.DefaultSubscriber
import com.arturogutierrez.openticator.view.presenter.Presenter
import javax.inject.Inject

class AddAccountFormPresenter @Inject constructor(val addAccountInteractorInteractor: AddAccountInteractor) : Presenter<AddAccountView> {

  lateinit override var view: AddAccountView

  override fun destroy() {
    addAccountInteractorInteractor.unsubscribe()
  }

  fun createTimeBasedAccount(accountName: String?, accountSecret: String?) {
    if (accountName == null || accountName.trim { it <= ' ' }.length == 0) {
      view.showFieldError(AddAccountView.FieldError.NAME)
    } else if (accountSecret == null || accountSecret.trim { it <= ' ' }.length == 0) {
      // TODO: Add validation to support only base 32 chars
      view.showFieldError(AddAccountView.FieldError.SECRET)
    } else {
      addAccountInteractorInteractor.configure(accountName.trim { it <= ' ' }, accountSecret.trim { it <= ' ' })
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
    view.dismissForm()
  }

  private fun onErrorAddingAccount() {
    view.unableToAddAccount()
  }
}
