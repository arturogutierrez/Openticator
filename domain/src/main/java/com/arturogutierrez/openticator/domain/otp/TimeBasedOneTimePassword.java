package com.arturogutierrez.openticator.domain.otp;

class TimeBasedOneTimePassword implements OneTimePassword {

  private static final int DEFAULT_TIME = 30;

  private final OneTimePasswordGenerator oneTimePasswordGenerator;

  public TimeBasedOneTimePassword(String secret) {
    this.oneTimePasswordGenerator = new OneTimePasswordGenerator(secret);
  }

  @Override
  public String generate() {
    throw new UnsupportedOperationException("Not implemented yet");
  }
}
