package com.arturogutierrez.openticator.domain.account.add.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import butterknife.Bind;
import com.arturogutierrez.openticator.R;
import com.arturogutierrez.openticator.domain.account.add.AddAccountFormPresenter;
import com.arturogutierrez.openticator.domain.account.add.AddAccountView;
import com.arturogutierrez.openticator.domain.account.add.di.AddAccountComponent;
import com.arturogutierrez.openticator.view.fragment.BaseFragment;
import javax.inject.Inject;

public class AddAccountFormFragment extends BaseFragment implements AddAccountView {

  @Inject
  AddAccountFormPresenter presenter;
  @Bind(R.id.et_account_name)
  AppCompatEditText etAccountName;
  @Bind(R.id.et_account_secret)
  AppCompatEditText etAccountSecret;

  public AddAccountFormFragment() {
    super();
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
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

    inflater.inflate(R.menu.account_add, menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_add:
        onAddAccountClicked();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
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
    return R.layout.fragment_account_add_manually;
  }

  private void initialize() {
    initializeInjector();
    presenter.setView(this);
  }

  private void initializeInjector() {
    getComponent(AddAccountComponent.class).inject(this);
  }

  private void onAddAccountClicked() {
    String accountName = etAccountName.getText().toString();
    String accountSecret = etAccountSecret.getText().toString();

    presenter.createTimeBasedAccount(accountName, accountSecret);
  }

  @Override
  public void dismissForm() {
    Activity activity = getActivity();
    if (activity != null) {
      activity.finish();
    }
  }

  @Override
  public void showFieldError(FieldError fieldError) {
    String message = null;

    if (fieldError == FieldError.NAME) {
      message = getString(R.string.please_set_account_name);
    } else if (fieldError == FieldError.SECRET) {
      message = getString(R.string.please_set_account_secret);
    }

    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
  }

  @Override
  public void unableToAddAccount() {
    Toast.makeText(getContext(), getString(R.string.unable_to_save_the_account), Toast.LENGTH_SHORT)
        .show();
    // TODO: Detect error and show a more friendly message
  }
}
