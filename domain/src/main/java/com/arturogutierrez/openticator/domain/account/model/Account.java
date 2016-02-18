package com.arturogutierrez.openticator.domain.account.model;

import com.arturogutierrez.openticator.domain.issuer.model.Issuer;

public class Account {

  private static final int MAX_ORDER = Integer.MAX_VALUE;

  private final String accountId;
  private final String name;
  private final OTPType type;
  private final String secret;
  private final Issuer issuer;
  private final int order;

  public Account(String accountId, String name, OTPType type, String secret, Issuer issuer) {
    this(accountId, name, type, secret, issuer, MAX_ORDER);
  }

  public Account(String accountId, String name, OTPType type, String secret, Issuer issuer,
      int order) {
    this.accountId = accountId;
    this.name = name;
    this.type = type;
    this.secret = secret;
    this.issuer = issuer;
    this.order = order;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Account)) {
      return false;
    }
    if (obj == this) {
      return true;
    }

    return getAccountId().equals(((Account) obj).getAccountId());
  }

  @Override
  public int hashCode() {
    return accountId.hashCode();
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

  public Issuer getIssuer() {
    return issuer;
  }

  public int getOrder() {
    return order;
  }
}
