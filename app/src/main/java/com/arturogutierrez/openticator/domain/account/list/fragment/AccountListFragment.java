package com.arturogutierrez.openticator.domain.account.list.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import butterknife.OnClick;
import com.arturogutierrez.openticator.R;
import com.arturogutierrez.openticator.domain.account.list.AccountListPresenter;
import com.arturogutierrez.openticator.domain.account.list.AccountListView;
import com.arturogutierrez.openticator.domain.account.list.di.AccountListComponent;
import com.arturogutierrez.openticator.domain.account.model.Account;
import com.arturogutierrez.openticator.domain.navigator.Navigator;
import com.arturogutierrez.openticator.view.fragment.BaseFragment;
import java.util.List;
import javax.inject.Inject;

public class AccountListFragment extends BaseFragment implements AccountListView {

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
    return R.layout.fragment_account_list;
  }

  @OnClick(R.id.fab_add_manually)
  public void onAddManuallyClicked() {
    navigator.navigateToAddAccountManually(getContext());
  }

  private void initialize() {
    initializeInjector();
    presenter.setView(this);
  }

  private void initializeInjector() {
    getComponent(AccountListComponent.class).inject(this);
  }

  @Override
  public void viewNoItems() {
    // TODO
  }

  @Override
  public void renderAccounts(List<Account> accounts) {
    // TODO
  }
}
