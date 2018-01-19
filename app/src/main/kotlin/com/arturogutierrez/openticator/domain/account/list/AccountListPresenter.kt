package com.arturogutierrez.openticator.domain.account.list

import android.os.Handler
import android.os.Looper
import com.arturogutierrez.openticator.domain.account.interactor.CopyToClipboardInteractor
import com.arturogutierrez.openticator.domain.account.interactor.GetAccountPasscodesInteractor
import com.arturogutierrez.openticator.domain.account.model.AccountPasscode
import com.arturogutierrez.openticator.domain.otp.time.RemainingTimeCalculator
import com.arturogutierrez.openticator.interactor.DefaultCompletableObserver
import com.arturogutierrez.openticator.interactor.DefaultFlowableObserver
import com.arturogutierrez.openticator.view.presenter.Presenter
import javax.inject.Inject

class AccountListPresenter @Inject constructor(
    private val getAccountPasscodesInteractor: GetAccountPasscodesInteractor,
    private val copyToClipboardInteractor: CopyToClipboardInteractor,
    private val remainingTimeCalculator: RemainingTimeCalculator) : Presenter<AccountListView>() {

  private val handler = Handler(Looper.getMainLooper())
  private val scheduleRunnable = Runnable { this.reloadPasscodes() }

  override fun resume() {
    loadAccountPasscodes()
  }

  override fun pause() {
    getAccountPasscodesInteractor.unsubscribe()
    copyToClipboardInteractor.unsubscribe()
    cancelSchedule()
  }

  override fun destroy() {
    getAccountPasscodesInteractor.unsubscribe()
    copyToClipboardInteractor.unsubscribe()
  }

  fun onEditModeList(isEditMode: Boolean) {
    if (isEditMode) {
      view?.startEditMode()
    }
  }

  fun onPasscodeSelected(accountPasscode: AccountPasscode) {
    val params = CopyToClipboardInteractor.Params(accountPasscode)
    copyToClipboardInteractor.execute(params, object : DefaultCompletableObserver() {
      override fun onComplete() {
        onPasscodeCopiedToClipboard()
      }
    })
  }

  private fun loadAccountPasscodes() {
    val params = GetAccountPasscodesInteractor.EmptyParams
    getAccountPasscodesInteractor.execute(params, object : DefaultFlowableObserver<List<AccountPasscode>>() {
      override fun onNext(t: List<AccountPasscode>) {
        onFetchAccountPasscodes(t)
      }

      override fun onError(e: Throwable) {
        onFetchError()
      }
    })
  }

  private fun onFetchAccountPasscodes(accountPasscodes: List<AccountPasscode>) {
    if (accountPasscodes.isEmpty()) {
      view?.noItems()
      return
    }

    view?.renderAccounts(accountPasscodes)
    scheduleUpdate(accountPasscodes)
  }

  private fun onFetchError() {
    view?.noItems()
  }

  private fun onPasscodeCopiedToClipboard() {
    view?.passcodeCopiedToClipboard()
  }

  private fun scheduleUpdate(accountPasscodes: List<AccountPasscode>) {
    val delayInSeconds = calculateMinimumSecondsUntilNextRefresh(accountPasscodes)
    handler.postDelayed(scheduleRunnable, (delayInSeconds * 1000).toLong())
  }

  private fun cancelSchedule() {
    handler.removeCallbacks(scheduleRunnable)
  }

  private fun reloadPasscodes() {
    getAccountPasscodesInteractor.unsubscribe()
    loadAccountPasscodes()
  }

  private fun calculateMinimumSecondsUntilNextRefresh(accountPasscodes: List<AccountPasscode>): Int {
    val minTime = accountPasscodes
        .map {
          remainingTimeCalculator.calculateRemainingSeconds(
              it.passcode.validUntil)
        }.min()

    return minTime ?: Integer.MAX_VALUE
  }
}
