package com.arturogutierrez.openticator.data.storage.realm.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class AccountRealm extends RealmObject {

  public static final String TOTP_TYPE = "TOTP";
  public static final String HOTP_TYPE = "HOTP";

  @PrimaryKey
  private String accountId;
  private String name;
  private String type;
  private String secret;
  private String issuer;

  public String getAccountId() {
    return accountId;
  }

  public void setAccountId(String accountId) {
    this.accountId = accountId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getSecret() {
    return secret;
  }

  public void setSecret(String secret) {
    this.secret = secret;
  }

  public String getIssuer() {
    return issuer;
  }

  public void setIssuer(String issuer) {
    this.issuer = issuer;
  }
}
