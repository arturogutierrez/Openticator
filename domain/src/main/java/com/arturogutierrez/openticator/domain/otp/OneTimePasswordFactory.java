package com.arturogutierrez.openticator.domain.otp;

import com.arturogutierrez.openticator.domain.account.model.Account;
import com.arturogutierrez.openticator.domain.account.model.OTPType;
import com.arturogutierrez.openticator.domain.otp.time.CurrentTimeProvider;
import com.arturogutierrez.openticator.domain.otp.time.TimeCalculator;
import com.arturogutierrez.openticator.domain.otp.time.TimeProvider;
import javax.inject.Inject;

public class OneTimePasswordFactory {

  private static final int DEFAULT_TIME_STEP_LENGTH = 30;
  private static final int DEFAULT_PASSWORD_LENGTH = 6;

  @Inject
  public OneTimePasswordFactory() {

  }

  public OneTimePassword createOneTimePassword(Account account, int timeCorrectionInMinutes) {
    if (account.getType() == OTPType.TOTP) {
      return createTimeBasedOneTimePassword(account, timeCorrectionInMinutes);
    }

    throw new UnsupportedOperationException("HTOP support not implemented yet");
  }

  private OneTimePassword createTimeBasedOneTimePassword(Account account,
      int timeCorrectionInMinutes) {
    OneTimePasswordGenerator oneTimePasswordGenerator =
        new OneTimePasswordGenerator(account.getSecret(), DEFAULT_PASSWORD_LENGTH);
    TimeProvider timeProvider = new CurrentTimeProvider();
    TimeCalculator timeCalculator =
        new TimeCalculator(timeProvider, DEFAULT_TIME_STEP_LENGTH, timeCorrectionInMinutes);
    return new TimeBasedOneTimePassword(oneTimePasswordGenerator, timeCalculator);
  }
}
