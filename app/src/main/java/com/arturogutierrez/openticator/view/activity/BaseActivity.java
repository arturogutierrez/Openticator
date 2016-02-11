package com.arturogutierrez.openticator.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.arturogutierrez.openticator.R;
import com.arturogutierrez.openticator.application.OpenticatorApplication;
import com.arturogutierrez.openticator.di.component.ApplicationComponent;
import com.arturogutierrez.openticator.di.module.ActivityModule;
import com.arturogutierrez.openticator.domain.navigator.Navigator;
import javax.inject.Inject;

public abstract class BaseActivity extends AppCompatActivity {

  @Bind(R.id.toolbar)
  protected Toolbar toolbar;

  @Inject
  protected Navigator navigator;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(getLayoutResource());

    getApplicationComponent().inject(this);
    ButterKnife.bind(this);

    configureToolbar();
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      finish();
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  protected ApplicationComponent getApplicationComponent() {
    return ((OpenticatorApplication) getApplication()).getApplicationComponent();
  }

  protected ActivityModule getActivityModule() {
    return new ActivityModule(this);
  }

  protected abstract int getLayoutResource();

  protected void addFragment(int containerViewId, Fragment fragment) {
    FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
    fragmentTransaction.add(containerViewId, fragment);
    fragmentTransaction.commit();
  }

  private void configureToolbar() {
    setSupportActionBar(toolbar);
  }
}
