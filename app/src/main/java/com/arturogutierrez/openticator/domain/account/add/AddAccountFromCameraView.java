package com.arturogutierrez.openticator.domain.account.add;

public interface AddAccountFromCameraView {

  void showLoading();

  void hideLoading();

  void openCaptureCode();

  void dismissScreen();

  void showQRError();
}
