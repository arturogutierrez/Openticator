package com.arturogutierrez.openticator.domain.password.wizard.di;

import com.arturogutierrez.openticator.di.PerActivity;
import com.arturogutierrez.openticator.di.component.ActivityComponent;
import com.arturogutierrez.openticator.di.component.ApplicationComponent;
import com.arturogutierrez.openticator.di.module.ActivityModule;
import com.arturogutierrez.openticator.domain.password.wizard.view.MasterPasswordFragment;
import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {
    ActivityModule.class, MasterPasswordModule.class
})
public interface MasterPasswordComponent extends ActivityComponent {
  void inject(MasterPasswordFragment masterPasswordFragment);
}
