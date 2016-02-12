package com.arturogutierrez.openticator.domain.account.model;

public class AccountPasscode {

  public static final int INFINITE = -1;

  private final String accountName;
  private final String passcode;
  private final Issuer issuer;
  private final long validUntil;

  public AccountPasscode(String accountName, Issuer issuer, String passcode, long validUntil) {
    this.accountName = accountName;
    this.passcode = passcode;
    this.issuer = issuer;
    this.validUntil = validUntil;
  }

  public String getAccountName() {
    return accountName;
  }

  public String getPasscode() {
    return passcode;
  }

  public Issuer getIssuer() {
    return issuer;
  }

  public long getValidUntil() {
    return validUntil;
  }
}
