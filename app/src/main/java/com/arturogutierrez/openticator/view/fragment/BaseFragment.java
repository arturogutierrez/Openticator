package com.arturogutierrez.openticator.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import com.arturogutierrez.openticator.di.HasComponent;
import com.arturogutierrez.openticator.view.activity.BaseActivity;

public abstract class BaseFragment extends Fragment {

  protected CoordinatorLayout coordinatorLayout;

  @SuppressWarnings("unchecked")
  protected <C> C getComponent(Class<C> componentType) {
    return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);

    if (context instanceof BaseActivity) {
      coordinatorLayout = ((BaseActivity) context).getCoordinatorLayout();
    }
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View fragmentView = inflater.inflate(getLayoutResource(), container, false);

    ButterKnife.bind(this, fragmentView);
    configureUI();

    return fragmentView;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();

    ButterKnife.unbind(this);
  }

  protected abstract int getLayoutResource();

  protected void configureUI() {
    // Nothing to do here
  }
}
