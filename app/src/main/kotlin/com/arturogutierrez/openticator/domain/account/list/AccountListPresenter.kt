package com.arturogutierrez.openticator.domain.account.list

import android.os.Handler
import android.os.Looper
import com.arturogutierrez.openticator.domain.account.interactor.GetAccountPasscodesInteractor
import com.arturogutierrez.openticator.domain.account.model.AccountPasscode
import com.arturogutierrez.openticator.domain.otp.time.RemainingTimeCalculator
import com.arturogutierrez.openticator.interactor.DefaultSubscriber
import com.arturogutierrez.openticator.view.presenter.Presenter
import javax.inject.Inject

@Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
class AccountListPresenter @Inject constructor(
    val getAccountPasscodesInteractor: GetAccountPasscodesInteractor,
    val remainingTimeCalculator: RemainingTimeCalculator) : Presenter<AccountListView> {

  private val handler = Handler(Looper.getMainLooper())
  private val scheduleRunnable = Runnable { this.reloadPasscodes() }

  lateinit override var view: AccountListView

  override fun resume() {
    loadAccountPasscodes()
  }

  override fun pause() {
    getAccountPasscodesInteractor.unsubscribe()
    cancelSchedule()
  }

  override fun destroy() {
    getAccountPasscodesInteractor.unsubscribe()
  }

  fun onEditModeList(isEditMode: Boolean) {
    if (isEditMode) {
      view.startEditMode()
    }
  }

  private fun loadAccountPasscodes() {
    getAccountPasscodesInteractor.execute(object : DefaultSubscriber<List<AccountPasscode>>() {
      override fun onNext(items: List<AccountPasscode>) {
        onFetchAccountPasscodes(items)
      }

      override fun onError(e: Throwable) {
        onFetchError()
      }
    })
  }

  private fun onFetchAccountPasscodes(accountPasscodes: List<AccountPasscode>) {
    if (accountPasscodes.size == 0) {
      view.noItems()
      return
    }

    view.renderAccounts(accountPasscodes)
    scheduleUpdate(accountPasscodes)
  }

  private fun onFetchError() {
    view.noItems()
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
    var minTime = Integer.MAX_VALUE

    for (accountPasscode in accountPasscodes) {
      val remainingTimeInSeconds = remainingTimeCalculator.calculateRemainingSeconds(
          accountPasscode.passcode.validUntil)
      minTime = Math.min(minTime, remainingTimeInSeconds)
    }

    return minTime
  }
}
