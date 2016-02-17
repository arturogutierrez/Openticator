package com.arturogutierrez.openticator.storage.realm.mapper;

import com.arturogutierrez.openticator.ApplicationTestCase;
import com.arturogutierrez.openticator.domain.account.model.Account;
import com.arturogutierrez.openticator.domain.issuer.model.Issuer;
import com.arturogutierrez.openticator.domain.account.model.OTPType;
import com.arturogutierrez.openticator.storage.realm.model.AccountRealm;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class AccountRealmMapperTest extends ApplicationTestCase {

  private static final String FAKE_ID = "1";
  private static final String FAKE_NAME = "tony@stark.com";
  private static final String FAKE_SECRET = "avengers";
  private static final Issuer FAKE_ISSUER = Issuer.GOOGLE;
  private static final int FAKE_ORDER = 1;

  private AccountRealmMapper accountRealmMapper;

  @Before
  public void setUp() {
    accountRealmMapper = new AccountRealmMapper();
  }

  @Test
  public void testNullAccountToRealm() {
    AccountRealm accountRealm = accountRealmMapper.transform((Account) null);

    assertThat(accountRealm, is(nullValue()));
  }

  @Test
  public void testAccountToAccountRealm() {
    Account account =
        new Account(FAKE_ID, FAKE_NAME, OTPType.TOTP, FAKE_SECRET, FAKE_ISSUER, FAKE_ORDER);

    AccountRealm accountRealm = accountRealmMapper.transform(account);

    assertThat(accountRealm.getAccountId(), is(FAKE_ID));
    assertThat(accountRealm.getName(), is(FAKE_NAME));
    assertThat(accountRealm.getSecret(), is(FAKE_SECRET));
    assertThat(accountRealm.getIssuer(), is(FAKE_ISSUER.getIdentifier()));
    assertThat(accountRealm.getOrder(), is(FAKE_ORDER));
  }

  @Test
  public void testHOTPTypeToRealm() {
    Account account =
        new Account(FAKE_ID, FAKE_NAME, OTPType.HOTP, FAKE_SECRET, FAKE_ISSUER, FAKE_ORDER);

    AccountRealm accountRealm = accountRealmMapper.transform(account);

    assertThat(accountRealm.getType(), is(AccountRealm.HOTP_TYPE));
  }

  @Test
  public void testTOTPTypeToRealm() {
    Account account =
        new Account(FAKE_ID, FAKE_NAME, OTPType.TOTP, FAKE_SECRET, FAKE_ISSUER, FAKE_ORDER);

    AccountRealm accountRealm = accountRealmMapper.transform(account);

    assertThat(accountRealm.getType(), is(AccountRealm.TOTP_TYPE));
  }

  @Test
  public void testNullAccountRealmToAccount() {
    Account account = accountRealmMapper.transform((AccountRealm) null);

    assertThat(account, is(nullValue()));
  }

  @Test
  public void testAccountRealmToAccount() {
    AccountRealm accountRealm = new AccountRealm();
    accountRealm.setAccountId(FAKE_ID);
    accountRealm.setName(FAKE_NAME);
    accountRealm.setType(AccountRealm.TOTP_TYPE);
    accountRealm.setSecret(FAKE_SECRET);
    accountRealm.setIssuer(FAKE_ISSUER.getIdentifier());

    Account account = accountRealmMapper.transform(accountRealm);

    assertThat(account.getAccountId(), is(FAKE_ID));
    assertThat(account.getName(), is(FAKE_NAME));
    assertThat(account.getSecret(), is(FAKE_SECRET));
    assertThat(account.getIssuer(), is(FAKE_ISSUER));
  }

  @Test
  public void testHOTPRealmTypeToOTPType() {
    AccountRealm accountRealm = new AccountRealm();
    accountRealm.setType(AccountRealm.HOTP_TYPE);

    Account account = accountRealmMapper.transform(accountRealm);

    assertThat(account.getType(), is(OTPType.HOTP));
  }

  @Test
  public void testTOTPRealmTypeToOTPType() {
    AccountRealm accountRealm = new AccountRealm();
    accountRealm.setType(AccountRealm.TOTP_TYPE);

    Account account = accountRealmMapper.transform(accountRealm);

    assertThat(account.getType(), is(OTPType.TOTP));
  }
}