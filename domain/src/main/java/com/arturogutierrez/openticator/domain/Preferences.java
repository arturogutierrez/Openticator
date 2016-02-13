package com.arturogutierrez.openticator.domain;

public interface Preferences {

  String PREFERENCES_NAME = "app_prefs";

  void reset();

  String getMasterPassword();

  void setMasterPassword(String masterPassword);
}
