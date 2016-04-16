package com.arturogutierrez.openticator.domain.account.list;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.arturogutierrez.openticator.domain.account.interactor.CreateExternalBackupInteractor;
import com.arturogutierrez.openticator.domain.account.interactor.GetAccountPasscodesInteractor;
import com.arturogutierrez.openticator.domain.account.interactor.ImportExternalBackupInteractor;
import com.arturogutierrez.openticator.domain.account.model.AccountPasscode;
import com.arturogutierrez.openticator.domain.backup.exceptions.EncryptionException;
import com.arturogutierrez.openticator.domain.otp.time.RemainingTimeCalculator;
import com.arturogutierrez.openticator.interactor.DefaultSubscriber;
import com.arturogutierrez.openticator.view.presenter.Presenter;
import java.util.List;
import javax.inject.Inject;

public class AccountListPresenter extends DefaultSubscriber<List<AccountPasscode>>
    implements Presenter {

  private final GetAccountPasscodesInteractor getAccountPasscodesInteractor;
  private final CreateExternalBackupInteractor createExternalBackupInteractor;
  private final ImportExternalBackupInteractor importExternalBackupInteractor;
  private final RemainingTimeCalculator remainingTimeCalculator;
  private AccountListView view;
  private Handler handler;
  private Runnable scheduleRunnable;

  @Inject
  public AccountListPresenter(GetAccountPasscodesInteractor getAccountPasscodesInteractor,
      CreateExternalBackupInteractor createExternalBackupInteractor,
      ImportExternalBackupInteractor importExternalBackupInteractor,
      RemainingTimeCalculator remainingTimeCalculator) {
    this.getAccountPasscodesInteractor = getAccountPasscodesInteractor;
    this.createExternalBackupInteractor = createExternalBackupInteractor;
    this.importExternalBackupInteractor = importExternalBackupInteractor;
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
      view.startEditMode();
    }
  }

  public void createBackup() {
    createExternalBackupInteractor.execute(new CreateBackupSubscriber());
  }

  public void importBackup(String password) {
    importExternalBackupInteractor.configure(password);
    importExternalBackupInteractor.execute(new ImportBackupSubscriber());
  }

  private void scheduleUpdate(List<AccountPasscode> accountPasscodes) {
    int delayInSeconds = calculateMinimumSecondsUntilNextRefresh(accountPasscodes);
    handler.postDelayed(scheduleRunnable, delayInSeconds * 1000);
  }

  private void cancelSchedule() {
    handler.removeCallbacks(scheduleRunnable);
  }

  private void reloadPasscodes() {
    getAccountPasscodesInteractor.unsubscribe();
    getAccountPasscodesInteractor.execute(this);
  }

  private int calculateMinimumSecondsUntilNextRefresh(List<AccountPasscode> accountPasscodes) {
    int minTime = Integer.MAX_VALUE;

    for (AccountPasscode accountPasscode : accountPasscodes) {
      int remainingTimeInSeconds = remainingTimeCalculator.calculateRemainingSeconds(
          accountPasscode.getPasscode().getValidUntilInSeconds());
      minTime = Math.min(minTime, remainingTimeInSeconds);
    }

    return minTime;
  }

  private class CreateBackupSubscriber extends DefaultSubscriber<String> {
    @Override
    public void onNext(String backupFilePath) {
      view.showBackupCreated(backupFilePath);
    }

    @Override
    public void onError(Throwable e) {
      if (e instanceof EncryptionException) {
        view.showEncryptionError();
      } else {
        view.showUnableToCreateBackupError();
      }
    }
  }

  private class ImportBackupSubscriber extends DefaultSubscriber<Integer> {
    @Override
    public void onNext(Integer numAccountsImported) {
      // TODO:
      Log.d("ImportBackupSubscriber", numAccountsImported + " accounts imported");
    }

    @Override
    public void onError(Throwable e) {
      e.printStackTrace();
    }
  }
}
