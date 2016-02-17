package com.arturogutierrez.openticator.domain.account;

import com.arturogutierrez.openticator.domain.account.model.Account;
import com.arturogutierrez.openticator.domain.account.model.OTPType;
import com.arturogutierrez.openticator.domain.issuer.IssuerDecoder;
import com.arturogutierrez.openticator.domain.issuer.model.Issuer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AccountFactoryTest {

  @Mock
  private IssuerDecoder mockIssuerDecoder;
  private AccountFactory accountFactory;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    accountFactory = new AccountFactory(mockIssuerDecoder);
  }

  @Test
  public void testTryingToDecodeIssuer() {
    when(mockIssuerDecoder.decode(anyString(), anyString())).thenReturn(Issuer.GOOGLE);

    accountFactory.createAccount(OTPType.TOTP, "name", "secret", "issuer");

    verify(mockIssuerDecoder).decode("name", "issuer");
  }

  @Test
  public void testCreateAccount() {
    when(mockIssuerDecoder.decode(anyString(), anyString())).thenReturn(Issuer.UNKNOWN);

    Account account = accountFactory.createAccount("name", "secret");

    assertThat(account.getAccountId(), is(notNullValue()));
    assertThat(account.getName(), is("name"));
    assertThat(account.getSecret(), is("secret"));
    assertThat(account.getType(), is(OTPType.TOTP));
    assertThat(account.getIssuer(), is(Issuer.UNKNOWN));
    assertThat(account.getOrder(), is(Integer.MAX_VALUE));
  }
}
