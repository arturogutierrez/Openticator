package com.arturogutierrez.openticator.domain.otp;

import com.arturogutierrez.openticator.domain.otp.model.Passcode;
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
  public Passcode generate() {
    long currentStep = timeCalculator.getCurrentTimeStep();
    long validUntilInSeconds = timeCalculator.getValidUntilInSeconds(currentStep);
    String code = oneTimePasswordGenerator.generate(currentStep);
    return new Passcode(code, validUntilInSeconds);
  }
}
