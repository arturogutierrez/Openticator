package com.arturogutierrez.openticator.domain.account;

import com.arturogutierrez.openticator.domain.account.model.Account;
import com.arturogutierrez.openticator.domain.account.model.OTPType;
import com.arturogutierrez.openticator.domain.issuer.IssuerDecoder;
import com.arturogutierrez.openticator.domain.issuer.model.Issuer;
import java.util.UUID;
import javax.inject.Inject;

public class AccountFactory {

  private final IssuerDecoder issuerDecoder;

  @Inject
  public AccountFactory(IssuerDecoder issuerDecoder) {
    this.issuerDecoder = issuerDecoder;
  }

  public Account createAccount(String name, String secret) {
    return createAccount(OTPType.TOTP, name, secret, null);
  }

  public Account createAccount(OTPType otpType, String name, String secret, String issuer) {
    String accountId = UUID.randomUUID().toString();
    Issuer decodedIssuer = issuerDecoder.decode(name, issuer);
    return new Account(accountId, name, otpType, secret, decodedIssuer);
  }

  public Account createAccount(Account account, String newName) {
    return new Account(account.getAccountId(), newName, account.getType(), account.getSecret(),
        account.getIssuer(), account.getCategory(), account.getOrder());
  }

  public Account createAccount(Account account, Issuer newIssuer) {
    return new Account(account.getAccountId(), account.getName(), account.getType(),
        account.getSecret(), newIssuer, account.getCategory(), account.getOrder());
  }
}
