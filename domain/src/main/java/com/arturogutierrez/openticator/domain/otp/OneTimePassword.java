package com.arturogutierrez.openticator.domain.otp;

import com.arturogutierrez.openticator.domain.otp.model.Passcode;

public interface OneTimePassword {

  Passcode generate();
}
