package com.arturogutierrez.openticator.domain.issuer;

import com.arturogutierrez.openticator.domain.issuer.model.Issuer;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class IssuerDecoderTest {

  private IssuerDecoder issuerDecoder;

  @Before
  public void setUp() {
    issuerDecoder = new IssuerDecoder();
  }

  @Test
  public void testEmptyTextsIsUnknown() {
    Issuer issuer = issuerDecoder.decode("", "");

    assertThat(issuer, is(Issuer.UNKNOWN));
  }

  @Test
  public void testMatchFullIssuerText() {
    Issuer issuer = issuerDecoder.decode("", "google");

    assertThat(issuer, is(Issuer.GOOGLE));
  }

  @Test
  public void testMatchPartialIssuerText() {
    Issuer issuer = issuerDecoder.decode("", "a google b");

    assertThat(issuer, is(Issuer.GOOGLE));
  }

  @Test
  public void testMatchFullAccountNameText() {
    Issuer issuer = issuerDecoder.decode("google", "");

    assertThat(issuer, is(Issuer.GOOGLE));
  }

  @Test
  public void testMatchPartialAccountNameText() {
    Issuer issuer = issuerDecoder.decode("a google b", "");

    assertThat(issuer, is(Issuer.GOOGLE));
  }

  @Test
  public void testAWSAccountName() {
    Issuer issuer = issuerDecoder.decode("root-account-mfa-device@1234567890", "");

    assertThat(issuer, is(Issuer.AWS));
  }

  @Test
  public void testBitcoinIssuer() {
    Issuer issuer = issuerDecoder.decode("local-bitcoins-account", "");

    assertThat(issuer, is(Issuer.BITCOIN));
  }

  @Test
  public void testDigitalOceanIssuer() {
    Issuer issuer = issuerDecoder.decode("", "digital ocean");
    Issuer issuer2 = issuerDecoder.decode("", "DigitalOcean");

    assertThat(issuer, is(Issuer.DIGITALOCEAN));
    assertThat(issuer2, is(Issuer.DIGITALOCEAN));
  }

  @Test
  public void testDropboxIssuer() {
    Issuer issuer = issuerDecoder.decode("", "dropbox");

    assertThat(issuer, is(Issuer.DROPBOX));
  }

  @Test
  public void testEvernoteIssuer() {
    Issuer issuer = issuerDecoder.decode("", "evernote");

    assertThat(issuer, is(Issuer.EVERNOTE));
  }

  @Test
  public void testFacebookIssuer() {
    Issuer issuer = issuerDecoder.decode("", "facebook");

    assertThat(issuer, is(Issuer.FACEBOOK));
  }

  @Test
  public void testGitHubIssuer() {
    Issuer issuer = issuerDecoder.decode("", "github");

    assertThat(issuer, is(Issuer.GITHUB));
  }

  @Test
  public void testGoogleIssuer() {
    Issuer issuer = issuerDecoder.decode("", "google");

    assertThat(issuer, is(Issuer.GOOGLE));
  }

  @Test
  public void testMicrosoftIssuer() {
    Issuer issuer = issuerDecoder.decode("", "microsoft");

    assertThat(issuer, is(Issuer.MICROSOFT));
  }

  @Test
  public void testSlackIssuer() {
    Issuer issuer = issuerDecoder.decode("", "slack");

    assertThat(issuer, is(Issuer.SLACK));
  }

  @Test
  public void testWordpressIssuer() {
    Issuer issuer = issuerDecoder.decode("", "wordpress");

    assertThat(issuer, is(Issuer.WORDPRESS));
  }
}
