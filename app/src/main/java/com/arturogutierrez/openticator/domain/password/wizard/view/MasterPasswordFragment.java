package com.arturogutierrez.openticator.domain.password.wizard.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.arturogutierrez.openticator.R;
import com.arturogutierrez.openticator.domain.password.wizard.di.MasterPasswordComponent;
import com.arturogutierrez.openticator.view.fragment.BaseFragment;

public class MasterPasswordFragment extends BaseFragment {

  public MasterPasswordFragment() {
    super();
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setHasOptionsMenu(true);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    initialize();
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);

    inflater.inflate(R.menu.master_password, menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_next:
        onNextClicked();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  private void initialize() {
    initializeInjector();
  }

  @Override
  protected int getLayoutResource() {
    return R.layout.fragment_master_password;
  }

  private void initializeInjector() {
    getComponent(MasterPasswordComponent.class).inject(this);
  }

  private void onNextClicked() {

  }
}
