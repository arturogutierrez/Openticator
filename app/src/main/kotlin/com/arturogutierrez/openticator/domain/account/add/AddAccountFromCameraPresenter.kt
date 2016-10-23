package com.arturogutierrez.openticator.domain.account.add

import android.content.Intent
import com.arturogutierrez.openticator.domain.account.AccountDecoder
import com.arturogutierrez.openticator.domain.account.camera.zxing.Intents
import com.arturogutierrez.openticator.domain.account.interactor.AddAccountInteractor
import com.arturogutierrez.openticator.domain.account.model.Account
import com.arturogutierrez.openticator.interactor.DefaultSubscriber
import com.arturogutierrez.openticator.view.presenter.Presenter
import javax.inject.Inject

class AddAccountFromCameraPresenter @Inject constructor(val addAccountInteractorInteractor: AddAccountInteractor,
                                                        val accountDecoder: AccountDecoder) : Presenter<AddAccountFromCameraView> {

  lateinit override var view: AddAccountFromCameraView

  override fun destroy() {
    addAccountInteractorInteractor.unsubscribe()
  }

  fun onScanQR() {
    view.showLoading()
    view.openCaptureCode()
  }

  fun onScannedQR(data: Intent) {
    val accountUri = data.getStringExtra(Intents.Scan.RESULT)
    if (accountUri == null) {
      showErrorAddingAccount()
      return
    }

    decodeAccount(accountUri)
  }

  private fun decodeAccount(accountUri: String) {
    val account = accountDecoder.decode(accountUri)
    if (account == null) {
      showErrorAddingAccount()
    } else {
      addAccountInteractorInteractor.configure(account.name, account.secret)
      addAccountInteractorInteractor.execute(object: DefaultSubscriber<Account>() {
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
    view.dismissScreen()
  }

  private fun onErrorAddingAccount() {
    showErrorAddingAccount()
  }

  private fun showErrorAddingAccount() {
    view.showQRError()
    view.hideLoading()
  }
}
