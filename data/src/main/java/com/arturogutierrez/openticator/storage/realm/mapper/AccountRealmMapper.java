package com.arturogutierrez.openticator.storage.realm.mapper;

import com.arturogutierrez.openticator.domain.account.model.Account;
import com.arturogutierrez.openticator.domain.account.model.OTPType;
import com.arturogutierrez.openticator.domain.issuer.model.Issuer;
import com.arturogutierrez.openticator.mapper.Mapper;
import com.arturogutierrez.openticator.storage.realm.model.AccountRealm;
import javax.inject.Inject;

public class AccountRealmMapper extends Mapper<Account, AccountRealm> {

  @Inject
  public AccountRealmMapper() {

  }

  @Override
  public AccountRealm transform(Account account) {
    AccountRealm accountRealm = null;

    if (account != null) {
      accountRealm = new AccountRealm();
      copyToAccountRealm(accountRealm, account);
    }

    return accountRealm;
  }

  @Override
  public Account reverseTransform(AccountRealm accountRealm) {
    Account account = null;

    if (accountRealm != null) {
      Issuer issuer = transformIssuer(accountRealm.getIssuer());
      account = new Account(accountRealm.getAccountId(), accountRealm.getName(),
          transformAccountType(accountRealm.getType()), accountRealm.getSecret(), issuer,
          accountRealm.getOrder());
    }

    return account;
  }

  public void copyToAccountRealm(AccountRealm accountRealm, Account account) {
    if (accountRealm == null || account == null) {
      return;
    }

    accountRealm.setAccountId(account.getAccountId());
    accountRealm.setName(account.getName());
    accountRealm.setSecret(account.getSecret());
    accountRealm.setIssuer(account.getIssuer().getIdentifier());
    accountRealm.setOrder(account.getOrder());
    accountRealm.setType(transformAccountType(account.getType()));
  }

  private OTPType transformAccountType(String otpType) {
    if (otpType.equals(AccountRealm.HOTP_TYPE)) {
      return OTPType.HOTP;
    }

    return OTPType.TOTP;
  }

  private String transformAccountType(OTPType otpType) {
    if (otpType == OTPType.HOTP) {
      return AccountRealm.HOTP_TYPE;
    }

    return AccountRealm.TOTP_TYPE;
  }

  private Issuer transformIssuer(String issuer) {
    return Issuer.Companion.fromString(issuer);
  }
}
