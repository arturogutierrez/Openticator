package com.arturogutierrez.openticator.domain.bootstrap;

import com.arturogutierrez.openticator.domain.Preferences;
import javax.inject.Inject;

public class InitialScreenSelector {

  private final Preferences preferences;

  @Inject
  public InitialScreenSelector(Preferences preferences) {
    this.preferences = preferences;
  }

  public boolean shouldShowWizard() {
    return preferences.getMasterPassword() == null;
  }
}
