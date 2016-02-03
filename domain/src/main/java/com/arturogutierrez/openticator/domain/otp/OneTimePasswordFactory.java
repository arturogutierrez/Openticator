package com.arturogutierrez.openticator.domain.otp;

import com.arturogutierrez.openticator.domain.model.Account;
import com.arturogutierrez.openticator.domain.model.OTPType;
import javax.inject.Inject;

public class OneTimePasswordFactory {

  @Inject
  public OneTimePasswordFactory() {

  }

  public OneTimePassword createOneTimePassword(Account account) {
    if (account.getType() == OTPType.TOTP) {
      return createTimeBasedOneTimePassword(account);
    }

    throw new UnsupportedOperationException("HTOP support not implemented yet");
  }

  private OneTimePassword createTimeBasedOneTimePassword(Account account) {
    return new TimeBasedOneTimePassword(account.getSecret());
  }
}
