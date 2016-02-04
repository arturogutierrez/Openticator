package com.arturogutierrez.openticator.domain.otp;

import com.arturogutierrez.openticator.domain.otp.time.TimeCalculator;

class TimeBasedOneTimePassword implements OneTimePassword {

  private final OneTimePasswordGenerator oneTimePasswordGenerator;
  private final TimeCalculator timeCalculator;

  public TimeBasedOneTimePassword(OneTimePasswordGenerator oneTimePasswordGenerator,
      TimeCalculator timeCalculator) {
    this.oneTimePasswordGenerator = oneTimePasswordGenerator;
    this.timeCalculator = timeCalculator;
  }

  @Override
  public String generate() {
    long currentStep = timeCalculator.getCurrentTimeStep();

    return oneTimePasswordGenerator.generate(currentStep);
  }
}
