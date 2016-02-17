package com.arturogutierrez.openticator.domain.account.list;

import android.os.Handler;
import android.os.Looper;
import com.arturogutierrez.openticator.domain.account.interactor.GetAccountPasscodesInteractor;
import com.arturogutierrez.openticator.domain.account.model.AccountPasscode;
import com.arturogutierrez.openticator.domain.otp.time.RemainingTimeCalculator;
import com.arturogutierrez.openticator.interactor.DefaultSubscriber;
import com.arturogutierrez.openticator.view.presenter.Presenter;
import java.util.List;
import javax.inject.Inject;

public class AccountListPresenter extends DefaultSubscriber<List<AccountPasscode>>
    implements Presenter {

  private final GetAccountPasscodesInteractor getAccountPasscodesInteractor;
  private final RemainingTimeCalculator remainingTimeCalculator;
  private AccountListView view;
  private Handler handler;
  private Runnable scheduleRunnable;

  @Inject
  public AccountListPresenter(GetAccountPasscodesInteractor getAccountPasscodesInteractor,
      RemainingTimeCalculator remainingTimeCalculator) {
    this.getAccountPasscodesInteractor = getAccountPasscodesInteractor;
    this.remainingTimeCalculator = remainingTimeCalculator;
    this.handler = new Handler(Looper.getMainLooper());
    this.scheduleRunnable = this::reloadPasscodes;
  }

  public void setView(AccountListView view) {
    this.view = view;
  }

  @Override
  public void resume() {
    getAccountPasscodesInteractor.execute(this);
  }

  @Override
  public void pause() {
    getAccountPasscodesInteractor.unsubscribe();
    cancelSchedule();
  }

  @Override
  public void destroy() {
    getAccountPasscodesInteractor.unsubscribe();
  }

  @Override
  public void onNext(List<AccountPasscode> accountPasscodes) {
    if (accountPasscodes == null || accountPasscodes.size() == 0) {
      view.viewNoItems();
      return;
    }

    view.renderAccounts(accountPasscodes);
    scheduleUpdate(accountPasscodes);
  }

  @Override
  public void onError(Throwable e) {
    view.viewNoItems();
  }

  public void onEditModeList(boolean isEditMode) {
    if (isEditMode) {
      view.showEditActionButtons();
    }
  }

  private void scheduleUpdate(List<AccountPasscode> accountPasscodes) {
    int delayInSeconds = calculateMinimunSecondsUntilNextRefresh(accountPasscodes);
    handler.postDelayed(scheduleRunnable, delayInSeconds * 1000);
  }

  private void cancelSchedule() {
    handler.removeCallbacks(scheduleRunnable);
  }

  private void reloadPasscodes() {
    getAccountPasscodesInteractor.unsubscribe();
    getAccountPasscodesInteractor.execute(this);
  }

  private int calculateMinimunSecondsUntilNextRefresh(List<AccountPasscode> accountPasscodes) {
    int minTime = Integer.MAX_VALUE;

    for (AccountPasscode accountPasscode : accountPasscodes) {
      int remainingTimeInSeconds = remainingTimeCalculator.calculateRemainingSeconds(
          accountPasscode.getPasscode().getValidUntilInSeconds());
      minTime = Math.min(minTime, remainingTimeInSeconds);
    }

    return minTime;
  }
}
