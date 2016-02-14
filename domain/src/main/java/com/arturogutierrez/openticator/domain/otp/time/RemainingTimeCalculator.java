package com.arturogutierrez.openticator.domain.otp.time;

import javax.inject.Inject;

public class RemainingTimeCalculator {

  private final CurrentTimeProvider timeProvider;

  @Inject
  public RemainingTimeCalculator(CurrentTimeProvider timeProvider) {
    this.timeProvider = timeProvider;
  }

  public int calculateRemainingSeconds(long validUntilInSeconds) {
    int remainingSeconds = (int) (validUntilInSeconds - timeProvider.getCurrentTimeInSeconds());
    if (remainingSeconds < 0) {
      remainingSeconds = 0;
    }

    return remainingSeconds;
  }
}
