package com.arturogutierrez.openticator.domain.account.list.di;

import com.arturogutierrez.openticator.di.PerActivity;
import com.arturogutierrez.openticator.di.component.ActivityComponent;
import com.arturogutierrez.openticator.di.component.ApplicationComponent;
import com.arturogutierrez.openticator.di.module.ActivityModule;
import com.arturogutierrez.openticator.domain.account.list.activity.AccountListActivity;
import com.arturogutierrez.openticator.domain.account.list.view.AccountEditActionMode;
import com.arturogutierrez.openticator.domain.account.list.view.AccountListFragment;
import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {
    ActivityModule.class, AccountListModule.class
})
public interface AccountListComponent extends ActivityComponent {

  void inject(AccountListActivity accountListActivity);

  void inject(AccountListFragment accountListFragment);

  void inject(AccountEditActionMode accountEditActionMode);
}
