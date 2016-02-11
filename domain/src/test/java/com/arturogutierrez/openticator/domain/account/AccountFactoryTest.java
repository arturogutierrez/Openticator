package com.arturogutierrez.openticator.domain.account;

import com.arturogutierrez.openticator.domain.account.model.Account;
import com.arturogutierrez.openticator.domain.account.model.OTPType;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class AccountFactoryTest {

  private AccountFactory accountFactory;

  @Before
  public void setUp() {
    accountFactory = new AccountFactory();
  }

  @Test
  public void testCreateAccount() {
    Account account = accountFactory.createAccount("name", "secret");

    assertThat(account.getAccountId(), is(notNullValue()));
    assertThat(account.getName(), is("name"));
    assertThat(account.getSecret(), is("secret"));
    assertThat(account.getType(), is(OTPType.TOTP));
    assertThat(account.getIssuer(), is(nullValue()));
    assertThat(account.getOrder(), is(Integer.MAX_VALUE));
  }
}
