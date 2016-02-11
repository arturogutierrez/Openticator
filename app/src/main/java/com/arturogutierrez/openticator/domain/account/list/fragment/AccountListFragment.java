package com.arturogutierrez.openticator.domain.account.list.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import com.arturogutierrez.openticator.R;
import com.arturogutierrez.openticator.domain.account.list.di.AccountListComponent;
import com.arturogutierrez.openticator.view.fragment.BaseFragment;

public class AccountListFragment extends BaseFragment {

  public AccountListFragment() {
    super();
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    initialize();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View fragmentView = inflater.inflate(R.layout.fragment_account_list, container, false);

    ButterKnife.bind(this, fragmentView);

    return fragmentView;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();

    ButterKnife.unbind(this);
  }

  private void initialize() {
    initializeInjector();
  }

  private void initializeInjector() {
    getComponent(AccountListComponent.class).inject(this);
  }
}
