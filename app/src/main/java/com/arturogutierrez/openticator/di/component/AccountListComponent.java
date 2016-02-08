package com.arturogutierrez.openticator.di.component;

import com.arturogutierrez.openticator.di.PerActivity;
import com.arturogutierrez.openticator.di.module.AccountListModule;
import com.arturogutierrez.openticator.di.module.ActivityModule;
import com.arturogutierrez.openticator.view.fragment.AccountListFragment;
import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {
    ActivityModule.class, AccountListModule.class
})
public interface AccountListComponent extends ActivityComponent {
  void inject(AccountListFragment accountListFragment);
}
