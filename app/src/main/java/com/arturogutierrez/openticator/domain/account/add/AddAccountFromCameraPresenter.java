package com.arturogutierrez.openticator.domain.account.add;

import android.content.Intent;
import com.arturogutierrez.openticator.domain.account.AccountDecoder;
import com.arturogutierrez.openticator.domain.account.camera.zxing.Intents;
import com.arturogutierrez.openticator.domain.account.interactor.AddAccountInteractor;
import com.arturogutierrez.openticator.domain.account.model.Account;
import com.arturogutierrez.openticator.interactor.DefaultSubscriber;
import com.arturogutierrez.openticator.view.presenter.Presenter;
import javax.inject.Inject;

public class AddAccountFromCameraPresenter extends DefaultSubscriber<Account> implements Presenter {

  private final AddAccountInteractor addAccountInteractorInteractor;
  private final AccountDecoder accountDecoder;
  private AddAccountFromCameraView view;

  @Inject
  public AddAccountFromCameraPresenter(AddAccountInteractor addAccountInteractorInteractor,
      AccountDecoder accountDecoder) {
    this.addAccountInteractorInteractor = addAccountInteractorInteractor;
    this.accountDecoder = accountDecoder;
  }

  public void setView(AddAccountFromCameraView view) {
    this.view = view;
  }

  @Override
  public void resume() {

  }

  @Override
  public void pause() {

  }

  @Override
  public void destroy() {
    addAccountInteractorInteractor.unsubscribe();
  }

  @Override
  public void onNext(Account account) {
    view.dismissScreen();
  }

  @Override
  public void onError(Throwable e) {
    showQRError();
  }

  public void onScanQR() {
    view.showLoading();
    view.openCaptureCode();
  }

  public void onScannedQR(Intent data) {
    String accountUri = data.getStringExtra(Intents.Scan.RESULT);
    if (accountUri == null) {
      showQRError();
      return;
    }

    decodeAccount(accountUri);
  }

  private void decodeAccount(String accountUri) {
    Account account = accountDecoder.decode(accountUri);
    if (account == null) {
      showQRError();
    } else {
      addAccountInteractorInteractor.configure(account.getName(), account.getSecret());
      addAccountInteractorInteractor.execute(this);
    }
  }

  private void showQRError() {
    view.showQRError();
    view.hideLoading();
  }
}
