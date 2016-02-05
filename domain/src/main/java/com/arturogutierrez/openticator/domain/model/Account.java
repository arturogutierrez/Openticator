package com.arturogutierrez.openticator.domain.model;

public class Account {

  private final String accountId;
  private final String name;
  private final OTPType type;
  private final String secret;
  private final String issuer;

  public Account(String accountId, String name, OTPType type, String secret, String issuer) {
    this.accountId = accountId;
    this.name = name;
    this.type = type;
    this.secret = secret;
    this.issuer = issuer;
  }

  public String getAccountId() {
    return accountId;
  }

  public String getName() {
    return name;
  }

  public OTPType getType() {
    return type;
  }

  public String getSecret() {
    return secret;
  }

  public String getIssuer() {
    return issuer;
  }
}
