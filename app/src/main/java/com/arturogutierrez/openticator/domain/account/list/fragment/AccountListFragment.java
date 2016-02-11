package com.arturogutierrez.openticator.domain.account.list.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import butterknife.OnClick;
import com.arturogutierrez.openticator.R;
import com.arturogutierrez.openticator.domain.account.list.AccountListPresenter;
import com.arturogutierrez.openticator.domain.account.list.di.AccountListComponent;
import com.arturogutierrez.openticator.domain.navigator.Navigator;
import com.arturogutierrez.openticator.view.fragment.BaseFragment;
import javax.inject.Inject;

public class AccountListFragment extends BaseFragment {

  @Inject
  Navigator navigator;
  @Inject
  AccountListPresenter presenter;

  public AccountListFragment() {
    super();
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    initialize();
  }

  @Override
  protected int getLayoutResource() {
    return R.layout.fragment_account_list;
  }

  @OnClick(R.id.fab_add_manually)
  public void onAddManuallyClicked() {
    navigator.navigateToAddAccountManually(getContext());
  }

  private void initialize() {
    initializeInjector();
  }

  private void initializeInjector() {
    getComponent(AccountListComponent.class).inject(this);
  }
}
