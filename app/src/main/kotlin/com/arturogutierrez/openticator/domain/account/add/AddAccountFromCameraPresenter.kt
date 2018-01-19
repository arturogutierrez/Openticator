package com.arturogutierrez.openticator.domain.account.add

import com.arturogutierrez.openticator.domain.account.AccountDecoder
import com.arturogutierrez.openticator.domain.account.interactor.AddAccountInteractor
import com.arturogutierrez.openticator.domain.account.interactor.AddAccountInteractor.Params
import com.arturogutierrez.openticator.domain.account.model.Account
import com.arturogutierrez.openticator.interactor.DefaultSingleObserver
import com.arturogutierrez.openticator.view.presenter.Presenter
import javax.inject.Inject

class AddAccountFromCameraPresenter @Inject constructor(private val addAccountInteractorInteractor: AddAccountInteractor,
                                                        private val accountDecoder: AccountDecoder) : Presenter<AddAccountFromCameraView>() {

  override fun destroy() {
    addAccountInteractorInteractor.dispose()
  }

  fun onScanQR() {
    view?.showLoading()
    view?.openCaptureCode()
  }

  fun onScannedQR(contents: String?) {
    if (contents == null) {
      view?.hideLoading()
      return
    }

    if (contents.isEmpty()) {
      showErrorAddingAccount()
      return
    }

    decodeAccount(contents)
  }

  private fun decodeAccount(accountUri: String) {
    val account = accountDecoder.decode(accountUri)
    if (account == null) {
      showErrorAddingAccount()
    } else {
      val params = Params(account)
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
    view?.dismissScreen()
  }

  private fun onErrorAddingAccount() {
    showErrorAddingAccount()
  }

  private fun showErrorAddingAccount() {
    view?.showQRError()
    view?.hideLoading()
  }
}
