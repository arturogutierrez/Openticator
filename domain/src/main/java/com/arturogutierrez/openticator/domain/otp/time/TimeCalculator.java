package com.arturogutierrez.openticator.domain.otp.time;

public class TimeCalculator {

  private final TimeProvider timeProvider;
  private final int timeStepLengthInSeconds;
  private final int timeCorrectionInMinutes;

  public TimeCalculator(TimeProvider timeProvider, int timeStepLengthInSeconds,
      int timeCorrectionInMinutes) {
    this.timeProvider = timeProvider;
    this.timeStepLengthInSeconds = timeStepLengthInSeconds;
    this.timeCorrectionInMinutes = timeCorrectionInMinutes;
  }

  public long getCurrentTimeStep() {
    return getTimeStep(timeProvider.getCurrentTimeInSeconds());
  }

  private long getTimeStep(long timeInSecods) {
    return (timeInSecods + timeCorrectionInMinutes) / timeStepLengthInSeconds;
  }
}
