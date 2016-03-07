package com.arturogutierrez.openticator.storage.export.mapper;

import com.arturogutierrez.openticator.domain.account.model.Account;
import com.arturogutierrez.openticator.domain.account.model.OTPType;
import com.arturogutierrez.openticator.domain.category.model.Category;
import com.arturogutierrez.openticator.domain.issuer.model.Issuer;
import com.arturogutierrez.openticator.mapper.Mapper;
import com.arturogutierrez.openticator.storage.export.model.AccountEntity;
import com.arturogutierrez.openticator.storage.export.model.CategoryEntity;
import javax.inject.Inject;

public class AccountEntityMapper extends Mapper<Account, AccountEntity> {

  private final CategoryEntityMapper categoryEntityMapper;

  @Inject
  public AccountEntityMapper(CategoryEntityMapper categoryEntityMapper) {
    this.categoryEntityMapper = categoryEntityMapper;
  }

  @Override
  public AccountEntity transform(Account account) {
    AccountEntity accountEntity = null;
    if (account != null) {
      CategoryEntity categoryEntity = categoryEntityMapper.transform(account.getCategory());
      accountEntity = new AccountEntity(account.getAccountId(), account.getName(),
          transformAccountType(account.getType()), account.getSecret(),
          account.getIssuer().getIdentifier(), categoryEntity, account.getOrder());
    }
    return accountEntity;
  }

  @Override
  public Account reverseTransform(AccountEntity accountEntity) {
    Account account = null;
    if (accountEntity != null) {
      Category category = categoryEntityMapper.reverseTransform(accountEntity.getCategory());
      account = new Account(accountEntity.getAccountId(), accountEntity.getName(),
          transformAccountType(accountEntity.getType()), accountEntity.getSecret(),
          transformIssuer(accountEntity.getIssuer()), category, accountEntity.getOrder());
    }
    return account;
  }

  private OTPType transformAccountType(String otpType) {
    if (otpType.equals(AccountEntity.HOTP_TYPE)) {
      return OTPType.HOTP;
    }

    return OTPType.TOTP;
  }

  private String transformAccountType(OTPType otpType) {
    if (otpType == OTPType.HOTP) {
      return AccountEntity.HOTP_TYPE;
    }

    return AccountEntity.TOTP_TYPE;
  }

  private Issuer transformIssuer(String issuer) {
    return Issuer.fromString(issuer);
  }
}
