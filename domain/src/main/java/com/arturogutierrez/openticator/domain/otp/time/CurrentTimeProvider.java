package com.arturogutierrez.openticator.domain.otp.time;

import javax.inject.Inject;

public class CurrentTimeProvider {

  @Inject
  public CurrentTimeProvider() {

  }

  public long getCurrentTimeInSeconds() {
    return System.currentTimeMillis() / 1000;
  }
}
