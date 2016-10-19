package com.arturogutierrez.openticator.domain.account;

import com.arturogutierrez.openticator.ApplicationTestCase;
import com.arturogutierrez.openticator.domain.account.model.Account;
import com.arturogutierrez.openticator.domain.account.model.OTPType;
import com.arturogutierrez.openticator.domain.issuer.IssuerDecoder;
import com.arturogutierrez.openticator.domain.issuer.model.Issuer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

public class AccountDecoderTest extends ApplicationTestCase {

  private IssuerDecoder issuerDecoder;
  private AccountFactory accountFactory;
  private AccountDecoder accountDecoder;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);

    issuerDecoder = new IssuerDecoder();
    accountFactory = new AccountFactory(issuerDecoder);
    accountDecoder = new AccountDecoder(accountFactory);
  }

  @Test
  public void testHOTPType() {
    String accountUri = "otpauth://hotp/a:b?secret=SECRET";

    Account account = accountDecoder.decode(accountUri);

    assertThat(account, is(notNullValue()));
    assertThat(account.getType(), is(OTPType.HOTP));
  }

  @Test
  public void testTOTPType() {
    String accountUri = "otpauth://totp/Openticator:tony.stark@starkindustries.com?secret=SECRET";

    Account account = accountDecoder.decode(accountUri);

    assertThat(account, is(notNullValue()));
    assertThat(account.getType(), is(OTPType.TOTP));
  }

  @Test
  public void testAccountNameIfThereIsAPair() {
    String accountUri = "otpauth://totp/Openticator:tony.stark@starkindustries.com?secret=SECRET";

    Account account = accountDecoder.decode(accountUri);

    assertThat(account, is(notNullValue()));
    assertThat(account.getType(), is(OTPType.TOTP));
    assertThat(account.getName(), is("tony.stark@starkindustries.com"));
  }

  @Test
  public void testAccountNameWithNoPair() {
    String accountUri = "otpauth://totp/tony.stark@starkindustries.com?secret=SECRET";

    Account account = accountDecoder.decode(accountUri);

    assertThat(account, is(notNullValue()));
    assertThat(account.getType(), is(OTPType.TOTP));
    assertThat(account.getName(), is("tony.stark@starkindustries.com"));
  }

  @Test
  public void testSecret() {
    String accountUri = "otpauth://totp/tony.stark@starkindustries.com?secret=SECRET";

    Account account = accountDecoder.decode(accountUri);

    assertThat(account, is(notNullValue()));
    assertThat(account.getType(), is(OTPType.TOTP));
    assertThat(account.getSecret(), is("SECRET"));
  }

  @Test
  public void testOpenticator() {
    String accountUri =
        "otpauth://totp/tony.stark@starkindustries.com?secret=SECRET&issuer=OPENTICATOR";

    Account account = accountDecoder.decode(accountUri);

    assertThat(account, is(notNullValue()));
    assertThat(account.getType(), is(OTPType.TOTP));
    assertThat(account.getIssuer(), is(Issuer.UNKNOWN));
  }

  @Test
  public void testDecodingHappyCase() {
    String accountUri =
        "otpauth://totp/Openticator:tony.stark@starkindustries.com?secret=ABCDEFGHASD&issuer=Openticator";

    Account account = accountDecoder.decode(accountUri);

    assertThat(account, is(notNullValue()));
    assertThat(account.getType(), is(OTPType.TOTP));
    assertThat(account.getName(), is("tony.stark@starkindustries.com"));
    assertThat(account.getSecret(), is("ABCDEFGHASD"));
    assertThat(account.getIssuer(), is(Issuer.UNKNOWN));
  }

  @Test
  public void testDecodingHappyCaseURLEncoded() {
    String accountUri =
        "otpauth://totp/Openticator%3Atony.stark%40starkindustries.com?secret=ABCDEFGHASD&issuer=Openticator";

    Account account = accountDecoder.decode(accountUri);

    assertThat(account, is(notNullValue()));
    assertThat(account.getType(), is(OTPType.TOTP));
    assertThat(account.getName(), is("tony.stark@starkindustries.com"));
    assertThat(account.getSecret(), is("ABCDEFGHASD"));
    assertThat(account.getIssuer(), is(Issuer.UNKNOWN));
  }
}
