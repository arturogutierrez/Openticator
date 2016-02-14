package com.arturogutierrez.openticator.domain.account;

import com.arturogutierrez.openticator.ApplicationTestCase;
import com.arturogutierrez.openticator.domain.account.model.OTPType;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

public class AccountDecoderTest extends ApplicationTestCase {

  @Mock
  private AccountFactory mockAccountFactory;

  private AccountDecoder accountDecoder;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);

    accountDecoder = new AccountDecoder(mockAccountFactory);
  }

  @Test
  public void testHOTPType() {
    String accountUri = "otpauth://hotp/a:b?secret=SECRET";

    accountDecoder.decode(accountUri);

    verify(mockAccountFactory).createAccount(eq(OTPType.HOTP), anyString(), anyString());
  }

  @Test
  public void testTOTPType() {
    String accountUri = "otpauth://totp/Openticator:tony.stark@starkindustries.com?secret=SECRET";

    accountDecoder.decode(accountUri);

    verify(mockAccountFactory).createAccount(eq(OTPType.TOTP), anyString(), anyString());
  }

  @Test
  public void testAccountNameIfThereIsAPair() {
    String accountUri = "otpauth://totp/Openticator:tony.stark@starkindustries.com?secret=SECRET";

    accountDecoder.decode(accountUri);

    verify(mockAccountFactory).createAccount(any(), eq("tony.stark@starkindustries.com"),
        anyString());
  }

  @Test
  public void testAccountNameWithNoPair() {
    String accountUri = "otpauth://totp/tony.stark@starkindustries.com?secret=SECRET";

    accountDecoder.decode(accountUri);

    verify(mockAccountFactory).createAccount(any(), eq("tony.stark@starkindustries.com"),
        anyString());
  }

  @Test
  public void testSecret() {
    String accountUri = "otpauth://totp/tony.stark@starkindustries.com?secret=SECRET";

    accountDecoder.decode(accountUri);

    verify(mockAccountFactory).createAccount(any(), anyString(), eq("SECRET"));
  }

  @Test
  public void testDecodingHappyCase() {
    String accountUri =
        "otpauth://totp/Openticator:tony.stark@starkindustries.com?secret=ABCDEFGHASD&issuer=Openticator";

    accountDecoder.decode(accountUri);

    verify(mockAccountFactory).createAccount(OTPType.TOTP, "tony.stark@starkindustries.com",
        "ABCDEFGHASD");
  }

  @Test
  public void testDecodingHappyCaseURLEncoded() {
    String accountUri =
        "otpauth://totp/Openticator%3Atony.stark%40starkindustries.com?secret=ABCDEFGHASD&issuer=Openticator";

    accountDecoder.decode(accountUri);

    verify(mockAccountFactory).createAccount(OTPType.TOTP, "tony.stark@starkindustries.com",
        "ABCDEFGHASD");
  }
}
