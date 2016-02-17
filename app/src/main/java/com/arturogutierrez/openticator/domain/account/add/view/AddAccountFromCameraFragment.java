package com.arturogutierrez.openticator.domain.account.add.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.OnClick;
import com.arturogutierrez.openticator.R;
import com.arturogutierrez.openticator.domain.account.add.AddAccountFromCameraPresenter;
import com.arturogutierrez.openticator.domain.account.add.AddAccountFromCameraView;
import com.arturogutierrez.openticator.domain.account.add.di.AddAccountComponent;
import com.arturogutierrez.openticator.domain.navigator.Navigator;
import com.arturogutierrez.openticator.view.fragment.BaseFragment;
import javax.inject.Inject;

public class AddAccountFromCameraFragment extends BaseFragment implements AddAccountFromCameraView {

  private static final int REQUEST_CODE = 0x10;

  @Inject
  AddAccountFromCameraPresenter presenter;
  @Inject
  Navigator navigator;
  @Bind(R.id.pb_loading)
  View pbLoading;
  @Bind(R.id.ll_scan_qr)
  View llScanQR;

  public AddAccountFromCameraFragment() {
    super();
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    initialize();
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode != REQUEST_CODE) {
      super.onActivityResult(requestCode, resultCode, data);
      return;
    }

    presenter.onScannedQR(data);
  }

  @Override
  public void onResume() {
    super.onResume();
    presenter.resume();
  }

  @Override
  public void onPause() {
    super.onPause();
    presenter.pause();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    presenter.destroy();
  }

  @Override
  protected int getLayoutResource() {
    return R.layout.fragment_account_add_from_camera;
  }

  @OnClick(R.id.ll_scan_qr)
  public void onScanQRClicked() {
    presenter.onScanQR();
  }

  private void initialize() {
    initializeInjector();
    presenter.setView(this);
  }

  private void initializeInjector() {
    getComponent(AddAccountComponent.class).inject(this);
  }

  @Override
  public void showLoading() {
    pbLoading.setVisibility(View.VISIBLE);
    llScanQR.setVisibility(View.GONE);
  }

  @Override
  public void hideLoading() {
    pbLoading.setVisibility(View.GONE);
    llScanQR.setVisibility(View.VISIBLE);
  }

  @Override
  public void openCaptureCode() {
    navigator.goToCaptureCode(getActivity(), this, REQUEST_CODE);
  }

  @Override
  public void dismissScreen() {
    Activity activity = getActivity();
    if (activity != null) {
      activity.finish();
    }
  }

  @Override
  public void showQRError() {
    Toast.makeText(getContext(), R.string.unable_to_decode_qr, Toast.LENGTH_SHORT).show();
  }
}
