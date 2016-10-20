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
    val remainingTimeCalculator: RemainingTimeCalculator) : DefaultSubscriber<List<AccountPasscode>>(), Presenter {

  private lateinit var view: AccountListView
  private val handler = Handler(Looper.getMainLooper())
  private val scheduleRunnable = Runnable { this.reloadPasscodes() }

  fun setView(view: AccountListView) {
    this.view = view
  }

  override fun resume() {
    getAccountPasscodesInteractor.execute(this)
  }

  override fun pause() {
    getAccountPasscodesInteractor.unsubscribe()
    cancelSchedule()
  }

  override fun destroy() {
    getAccountPasscodesInteractor.unsubscribe()
  }

  override fun onNext(accountPasscodes: List<AccountPasscode>) {
    if (accountPasscodes.size == 0) {
      view.viewNoItems()
      return
    }

    view.renderAccounts(accountPasscodes)
    scheduleUpdate(accountPasscodes)
  }

  override fun onError(e: Throwable) {
    view.viewNoItems()
  }

  fun onEditModeList(isEditMode: Boolean) {
    if (isEditMode) {
      view.startEditMode()
    }
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
    getAccountPasscodesInteractor.execute(this)
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
