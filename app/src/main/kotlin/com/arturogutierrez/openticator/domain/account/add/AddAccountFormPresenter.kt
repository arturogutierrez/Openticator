package com.arturogutierrez.openticator.domain.account.add

import com.arturogutierrez.openticator.domain.account.interactor.AddAccountInteractor
import com.arturogutierrez.openticator.domain.account.model.Account
import com.arturogutierrez.openticator.interactor.DefaultSubscriber
import com.arturogutierrez.openticator.view.presenter.Presenter
import javax.inject.Inject

class AddAccountFormPresenter @Inject constructor(val addAccountInteractorInteractor: AddAccountInteractor) : DefaultSubscriber<Account>(), Presenter {

  private lateinit var view: AddAccountView

  fun setView(view: AddAccountView) {
    this.view = view
  }

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
      addAccountInteractorInteractor.execute(this)
    }
  }

  override fun onNext(item: Account) {
    view.dismissForm()
  }

  override fun onError(e: Throwable) {
    view.unableToAddAccount()
  }
}
