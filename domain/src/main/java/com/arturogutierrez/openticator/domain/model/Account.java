package com.arturogutierrez.openticator.domain.model;

public class Account {

  private final String name;
  private final OTPType type;
  private final String secret;
  private final String issuer;

  public Account(String name, OTPType type, String secret, String issuer) {
    this.name = name;
    this.type = type;
    this.secret = secret;
    this.issuer = issuer;
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
