package com.arturogutierrez.openticator.storage.export.model;

public class AccountEntity {

  public static final String TOTP_TYPE = "TOTP";
  public static final String HOTP_TYPE = "HOTP";

  private final String accountId;
  private final String name;
  private final String type;
  private final String secret;
  private final String issuer;
  private final CategoryEntity category;
  private final int order;

  public AccountEntity(String accountId, String name, String type, String secret, String issuer,
      CategoryEntity category, int order) {
    this.accountId = accountId;
    this.name = name;
    this.type = type;
    this.secret = secret;
    this.issuer = issuer;
    this.category = category;
    this.order = order;
  }

  public String getAccountId() {
    return accountId;
  }

  public String getName() {
    return name;
  }

  public String getType() {
    return type;
  }

  public String getSecret() {
    return secret;
  }

  public String getIssuer() {
    return issuer;
  }

  public CategoryEntity getCategory() {
    return category;
  }

  public int getOrder() {
    return order;
  }
}
