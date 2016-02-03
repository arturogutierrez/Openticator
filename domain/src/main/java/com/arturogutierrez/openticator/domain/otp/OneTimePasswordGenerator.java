package com.arturogutierrez.openticator.domain.otp;

class OneTimePasswordGenerator {

  private final String secret;

  public OneTimePasswordGenerator(String secret) {
    this.secret = secret;
  }

  public String generate(long state) {
    throw new UnsupportedOperationException("Not implemented yet");
  }
}
