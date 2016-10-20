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
                                                        val accountDecoder: AccountDecoder) : DefaultSubscriber<Account>(), Presenter {

  private lateinit var view: AddAccountFromCameraView

  fun setView(view: AddAccountFromCameraView) {
    this.view = view
  }

  override fun destroy() {
    addAccountInteractorInteractor.unsubscribe()
  }

  override fun onNext(item: Account) {
    view.dismissScreen()
  }

  override fun onError(e: Throwable) {
    showQRError()
  }

  fun onScanQR() {
    view.showLoading()
    view.openCaptureCode()
  }

  fun onScannedQR(data: Intent) {
    val accountUri = data.getStringExtra(Intents.Scan.RESULT)
    if (accountUri == null) {
      showQRError()
      return
    }

    decodeAccount(accountUri)
  }

  private fun decodeAccount(accountUri: String) {
    val account = accountDecoder.decode(accountUri)
    if (account == null) {
      showQRError()
    } else {
      addAccountInteractorInteractor.configure(account.name, account.secret)
      addAccountInteractorInteractor.execute(this)
    }
  }

  private fun showQRError() {
    view.showQRError()
    view.hideLoading()
  }
}
