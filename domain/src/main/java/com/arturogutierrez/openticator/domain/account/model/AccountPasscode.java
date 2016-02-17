package com.arturogutierrez.openticator.domain.account.model;

import com.arturogutierrez.openticator.domain.issuer.model.Issuer;
import com.arturogutierrez.openticator.domain.otp.model.Passcode;

public class AccountPasscode {

  private final Account account;
  private final Passcode passcode;
  private final Issuer issuer;

  public AccountPasscode(Account account, Issuer issuer, Passcode passcode) {
    this.account = account;
    this.passcode = passcode;
    this.issuer = issuer;
  }

  public Account getAccount() {
    return account;
  }

  public Passcode getPasscode() {
    return passcode;
  }

  public Issuer getIssuer() {
    return issuer;
  }
}
