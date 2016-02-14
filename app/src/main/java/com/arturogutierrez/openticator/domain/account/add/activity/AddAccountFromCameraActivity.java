package com.arturogutierrez.openticator.domain.account.add.activity;

import android.hardware.Camera;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import butterknife.Bind;
import com.arturogutierrez.openticator.R;
import com.arturogutierrez.openticator.view.activity.BaseActivity;
import com.google.zxing.client.android.camera.CameraConfigurationUtils;

public class AddAccountFromCameraActivity extends BaseActivity implements SurfaceHolder.Callback {

  private static final int ZOOM = 2;

  @Bind(R.id.sv_view)
  SurfaceView cameraSurfaceView;

  private SurfaceHolder cameraSurfaceHolder;
  private boolean hasSurface;
  private Camera camera;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Window window = getWindow();
    window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
  }

  @Override
  protected void onResume() {
    super.onResume();

    SurfaceHolder surfaceHolder = cameraSurfaceView.getHolder();
    if (surfaceHolder == null) {
      dismissDueToCameraError();
      return;
    }

    if (hasSurface) {
      initializeCamera(surfaceHolder);
    } else {
      cameraSurfaceHolder = surfaceHolder;
      cameraSurfaceHolder.addCallback(this);
    }
  }

  @Override
  protected void onPause() {
    if (camera != null) {
      stopCamera();
    }

    if (cameraSurfaceHolder != null) {
      cameraSurfaceHolder.removeCallback(this);
      cameraSurfaceHolder = null;
    }

    super.onPause();
  }

  @Override
  protected int getLayoutResource() {
    return R.layout.activity_camera;
  }

  private void initializeCamera(SurfaceHolder surfaceHolder) {
    try {
      camera = Camera.open();
    } catch(Exception e) {
      e.printStackTrace();
      dismissDueToCameraError();
      return;
    }

    if (camera == null) {
      dismissDueToCameraError();
      return;
    }

    configureCameraParameters(camera);

    try {
      camera.setPreviewDisplay(surfaceHolder);
      camera.startPreview();
    } catch (Exception e) {
      dismissDueToCameraError();
    }
  }

  private void configureCameraParameters(Camera camera) {
    Camera.Parameters parameters = camera.getParameters();
    CameraConfigurationUtils.setBestPreviewFPS(parameters);
    CameraConfigurationUtils.setBarcodeSceneMode(parameters);
    CameraConfigurationUtils.setVideoStabilization(parameters);
    CameraConfigurationUtils.setMetering(parameters);
    CameraConfigurationUtils.setZoom(parameters, ZOOM);
    camera.setDisplayOrientation(90);
    camera.setParameters(parameters);
  }

  private void dismissDueToCameraError() {
    dismissWithError(getString(R.string.unable_to_open_the_camera));
  }

  private void dismissWithError(String message) {
    showErrorMessage(message);
    finish();
  }

  private void showErrorMessage(String message) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
  }

  @Override
  public void surfaceCreated(SurfaceHolder holder) {
    if (!hasSurface) {
      hasSurface = true;
      initializeCamera(holder);
    }
  }

  @Override
  public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    // Nothing
  }

  @Override
  public void surfaceDestroyed(SurfaceHolder holder) {
    cameraSurfaceHolder = null;
    hasSurface = false;
  }

  private void stopCamera() {
    camera.stopPreview();
    camera.release();
    camera = null;
  }
}
