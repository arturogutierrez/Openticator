package com.arturogutierrez.openticator.domain.account.list;

import android.os.Handler;
import android.os.Looper;
import com.arturogutierrez.openticator.domain.account.interactor.GetAccountPasscodesInteractor;
import com.arturogutierrez.openticator.domain.account.model.AccountPasscode;
import com.arturogutierrez.openticator.interactor.DefaultSubscriber;
import com.arturogutierrez.openticator.view.presenter.Presenter;
import java.util.List;
import javax.inject.Inject;

public class AccountListPresenter extends DefaultSubscriber<List<AccountPasscode>>
    implements Presenter {

  private final GetAccountPasscodesInteractor getAccountPasscodesInteractor;
  private AccountListView view;
  private Handler handler;
  private Runnable scheduleRunnable;

  @Inject
  public AccountListPresenter(GetAccountPasscodesInteractor getAccountPasscodesInteractor) {
    this.getAccountPasscodesInteractor = getAccountPasscodesInteractor;
    this.handler = new Handler(Looper.getMainLooper());
    this.scheduleRunnable = () -> {
      reloadPasscodes();
      scheduleUpdate();
    };
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
    scheduleUpdate();
  }

  @Override
  public void onError(Throwable e) {
    view.viewNoItems();
  }

  private void scheduleUpdate() {
    // TODO: Calculate delayed time exactly
    handler.postDelayed(scheduleRunnable, 30 * 1000);
  }

  private void cancelSchedule() {
    handler.removeCallbacks(scheduleRunnable);
  }

  private void reloadPasscodes() {
    getAccountPasscodesInteractor.unsubscribe();
    getAccountPasscodesInteractor.execute(this);
  }
}
