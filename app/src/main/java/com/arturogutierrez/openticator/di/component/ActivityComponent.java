package com.arturogutierrez.openticator.di.component;

import android.app.Activity;
import com.arturogutierrez.openticator.di.PerActivity;
import com.arturogutierrez.openticator.di.module.ActivityModule;
import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
  Activity activity();
}
