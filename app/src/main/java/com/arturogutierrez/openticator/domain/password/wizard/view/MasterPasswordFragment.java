package com.arturogutierrez.openticator.domain.password.wizard.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.Bind;
import com.arturogutierrez.openticator.R;
import com.arturogutierrez.openticator.domain.navigator.Navigator;
import com.arturogutierrez.openticator.domain.password.wizard.MasterPasswordPresenter;
import com.arturogutierrez.openticator.domain.password.wizard.di.MasterPasswordComponent;
import com.arturogutierrez.openticator.view.fragment.BaseFragment;
import javax.inject.Inject;

public class MasterPasswordFragment extends BaseFragment implements MasterPasswordView {

  @Inject
  Navigator navigator;
  @Inject
  MasterPasswordPresenter presenter;

  @Bind(R.id.et_password)
  EditText etPassword;
  @Bind(R.id.et_confirm_password)
  EditText etConfirmPassword;

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
  protected int getLayoutResource() {
    return R.layout.fragment_master_password;
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
    presenter.setView(this);
  }

  private void initializeInjector() {
    getComponent(MasterPasswordComponent.class).inject(this);
  }

  private void onNextClicked() {
    String password = etPassword.getText().toString();
    String confirmPassword = etConfirmPassword.getText().toString();
    presenter.createMasterPassword(password, confirmPassword);
  }

  private void showError(String message) {
    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
  }

  @Override
  public void showWeakPasswordError() {
    showError(getString(R.string.your_password_is_weak));
  }

  @Override
  public void showMismatchPasswordsError() {
    showError(getString(R.string.the_passwords_are_not_equal));
  }

  @Override
  public void closeWizard() {
    navigator.goToAccountList(getContext());
    Activity activity = getActivity();
    if (activity != null) {
      activity.finish();
    }
  }
}
