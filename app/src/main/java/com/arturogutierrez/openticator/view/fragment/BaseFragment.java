package com.arturogutierrez.openticator.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import com.arturogutierrez.openticator.di.HasComponent;

public abstract class BaseFragment extends Fragment {

  @SuppressWarnings("unchecked")
  protected <C> C getComponent(Class<C> componentType) {
    return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View fragmentView = inflater.inflate(getLayoutResource(), container, false);

    ButterKnife.bind(this, fragmentView);

    return fragmentView;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();

    ButterKnife.unbind(this);
  }

  protected abstract int getLayoutResource();
}
