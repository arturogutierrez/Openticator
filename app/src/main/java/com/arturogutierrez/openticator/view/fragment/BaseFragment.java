package com.arturogutierrez.openticator.view.fragment;

import android.support.v4.app.Fragment;
import com.arturogutierrez.openticator.di.HasComponent;

public abstract class BaseFragment extends Fragment {

  @SuppressWarnings("unchecked")
  protected <C> C getComponent(Class<C> componentType) {
    return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
  }
}
