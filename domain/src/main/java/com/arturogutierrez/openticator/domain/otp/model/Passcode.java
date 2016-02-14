package com.arturogutierrez.openticator.domain.otp.model;

public class Passcode {
  public static final int INFINITE = -1;

  private final String code;
  private final long validUntil;

  public Passcode(String code, long validUntil) {
    this.code = code;
    this.validUntil = validUntil;
  }

  public Passcode(String code) {
    this(code, INFINITE);
  }

  public String getCode() {
    return code;
  }

  public long getValidUntilInSeconds() {
    return validUntil;
  }
}
