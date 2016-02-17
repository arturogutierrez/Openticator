package com.arturogutierrez.openticator.domain.account.model;

import com.arturogutierrez.openticator.domain.issuer.model.Issuer;
import com.arturogutierrez.openticator.domain.otp.model.Passcode;

public class AccountPasscode {

  private final String accountName;
  private final Passcode passcode;
  private final Issuer issuer;

  public AccountPasscode(String accountName, Issuer issuer, Passcode passcode) {
    this.accountName = accountName;
    this.passcode = passcode;
    this.issuer = issuer;
  }

  public String getAccountName() {
    return accountName;
  }

  public Passcode getPasscode() {
    return passcode;
  }

  public Issuer getIssuer() {
    return issuer;
  }
}
