package com.arturogutierrez.openticator.domain.otp.time;

public class TimeCalculator {

  private final CurrentTimeProvider timeProvider;
  private final int timeStepLengthInSeconds;
  private final int timeCorrectionInSeconds;

  public TimeCalculator(CurrentTimeProvider timeProvider, int timeStepLengthInSeconds,
      int timeCorrectionInSeconds) {
    this.timeProvider = timeProvider;
    this.timeStepLengthInSeconds = timeStepLengthInSeconds;
    this.timeCorrectionInSeconds = timeCorrectionInSeconds;
  }

  public long getCurrentTimeStep() {
    return getTimeStep(timeProvider.getCurrentTimeInSeconds());
  }

  public long getValidUntilInSeconds(long timeStep) {
    return (timeStep + 1) * timeStepLengthInSeconds - timeCorrectionInSeconds;
  }

  private long getTimeStep(long timeInSecods) {
    return (timeInSecods + timeCorrectionInSeconds) / timeStepLengthInSeconds;
  }
}
