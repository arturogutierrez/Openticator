package com.arturogutierrez.openticator.domain.account.add.di;

import com.arturogutierrez.openticator.di.PerActivity;
import com.arturogutierrez.openticator.di.component.ActivityComponent;
import com.arturogutierrez.openticator.di.component.ApplicationComponent;
import com.arturogutierrez.openticator.di.module.ActivityModule;
import com.arturogutierrez.openticator.domain.account.add.view.AddAccountFormFragment;
import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {
    ActivityModule.class, AddAccountModule.class
})
public interface AddAccountComponent extends ActivityComponent {
  void inject(AddAccountFormFragment addAccountFormFragment);
}
