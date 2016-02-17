package com.arturogutierrez.openticator.domain.account;

import android.net.Uri;
import com.arturogutierrez.openticator.domain.account.model.Account;
import com.arturogutierrez.openticator.domain.account.model.OTPType;
import javax.inject.Inject;

public class AccountDecoder {

  private static final String SCHEME = "otpauth";
  private static final String TOTP = "totp";
  private static final String HOTP = "hotp";
  private static final String SECRET = "secret";
  private static final String ISSUER = "issuer";

  private final AccountFactory accountFactory;

  @Inject
  public AccountDecoder(AccountFactory accountFactory) {
    this.accountFactory = accountFactory;
  }

  public Account decode(String accountUri) {
    Uri uri = Uri.parse(accountUri);

    if (uri == null) {
      return null;
    }

    if (!uri.getScheme().toLowerCase().equalsIgnoreCase(SCHEME)) {
      return null;
    }

    return decodeAccount(uri);
  }

  private Account decodeAccount(Uri uri) {
    String otpString = uri.getAuthority().toLowerCase();
    String secret = uri.getQueryParameter(SECRET);
    String issuer = uri.getQueryParameter(ISSUER);
    String accountName = getAccountName(uri.getPath());
    if (!isValidType(otpString) || !isValidSecret(secret) || accountName == null) {
      return null;
    }

    OTPType otpType = getOTPType(otpString);
    // Try to get only right part if the account name has the template (issuer:account)
    String accountNamePair[] = accountName.split(":");
    if (accountNamePair.length == 2) {
      accountName = accountNamePair[1];
    }

    return accountFactory.createAccount(otpType, accountName, secret, issuer);
  }

  private boolean isValidType(String otpTypeInLowercase) {
    return otpTypeInLowercase.equals(HOTP) || otpTypeInLowercase.equals(TOTP);
  }

  private OTPType getOTPType(String otpString) {
    if (otpString.equalsIgnoreCase(HOTP)) {
      return OTPType.HOTP;
    }
    return OTPType.TOTP;
  }

  private boolean isValidSecret(String secret) {
    return secret != null && secret.length() > 0;
  }

  private String getAccountName(String path) {
    if (path == null || !path.startsWith("/")) {
      return null;
    }

    // Remove leading '/'
    String accountName = path.substring(1).trim();
    if (accountName.length() == 0) {
      return null;
    }

    return accountName;
  }
}
