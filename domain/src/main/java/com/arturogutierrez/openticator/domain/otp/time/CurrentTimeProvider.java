package com.arturogutierrez.openticator.domain.otp.time;

public class CurrentTimeProvider implements TimeProvider {

  @Override
  public long getCurrentTimeInSeconds() {
    return System.currentTimeMillis();
  }
}
