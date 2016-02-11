package com.arturogutierrez.openticator.domain.account.model;

public class Account {

  private static final int MAX_ORDER = Integer.MAX_VALUE;

  private final String accountId;
  private final String name;
  private final OTPType type;
  private final String secret;
  private final String issuer;
  private final int order;

  public Account(String accountId, String name, OTPType type, String secret) {
    this(accountId, name, type, secret, null, MAX_ORDER);
  }

  public Account(String accountId, String name, OTPType type, String secret, String issuer,
      int order) {
    this.accountId = accountId;
    this.name = name;
    this.type = type;
    this.secret = secret;
    this.issuer = issuer;
    this.order = order;
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

  public int getOrder() {
    return order;
  }
}
