package com.arturogutierrez.openticator.view.activity;

import android.os.Bundle;
import butterknife.ButterKnife;
import com.arturogutierrez.openticator.R;

public class MainActivity extends BaseActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    initializeActivity(savedInstanceState);
  }

  @Override
  protected int getLayoutResource() {
    return R.layout.activity_main;
  }

  private void initializeActivity(Bundle savedInstanceState) {
    if (savedInstanceState == null) {
      configureInjector();
    }
  }

  private void configureInjector() {
    ButterKnife.bind(this);
  }
}
