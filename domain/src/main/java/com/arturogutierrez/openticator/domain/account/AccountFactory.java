package com.arturogutierrez.openticator.domain.account;

import com.arturogutierrez.openticator.domain.account.model.Account;
import com.arturogutierrez.openticator.domain.account.model.OTPType;
import java.util.UUID;
import javax.inject.Inject;

public class AccountFactory {

  @Inject
  public AccountFactory() {

  }

  public Account createAccount(String name, String secret) {
    String accountId = UUID.randomUUID().toString();

    return new Account(accountId, name, OTPType.TOTP, secret);
  }
}
