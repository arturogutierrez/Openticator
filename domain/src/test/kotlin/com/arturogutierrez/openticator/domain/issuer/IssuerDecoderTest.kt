package com.arturogutierrez.openticator.domain.issuer

import com.arturogutierrez.openticator.domain.issuer.model.Issuer
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

class IssuerDecoderTest {

  private lateinit var issuerDecoder: IssuerDecoder

  @Before
  fun setUp() {
    issuerDecoder = IssuerDecoder()
  }

  @Test
  fun testEmptyTextsIsUnknown() {
    val issuer = issuerDecoder.decode("", "")

    assertThat(issuer, `is`(Issuer.UNKNOWN))
  }

  @Test
  fun testMatchFullIssuerText() {
    val issuer = issuerDecoder.decode("", "google")

    assertThat(issuer, `is`(Issuer.GOOGLE))
  }

  @Test
  fun testMatchPartialIssuerText() {
    val issuer = issuerDecoder.decode("", "a google b")

    assertThat(issuer, `is`(Issuer.GOOGLE))
  }

  @Test
  fun testMatchFullAccountNameText() {
    val issuer = issuerDecoder.decode("google", "")

    assertThat(issuer, `is`(Issuer.GOOGLE))
  }

  @Test
  fun testMatchPartialAccountNameText() {
    val issuer = issuerDecoder.decode("a google b", "")

    assertThat(issuer, `is`(Issuer.GOOGLE))
  }

  @Test
  fun testAWSAccountName() {
    val issuer = issuerDecoder.decode("root-account-mfa-device@1234567890", "")

    assertThat(issuer, `is`(Issuer.AWS))
  }

  @Test
  fun testBitcoinIssuer() {
    val issuer = issuerDecoder.decode("local-bitcoins-account", "")

    assertThat(issuer, `is`(Issuer.BITCOIN))
  }

  @Test
  fun testDigitalOceanIssuer() {
    val issuer = issuerDecoder.decode("", "digital ocean")
    val issuer2 = issuerDecoder.decode("", "DigitalOcean")

    assertThat(issuer, `is`(Issuer.DIGITALOCEAN))
    assertThat(issuer2, `is`(Issuer.DIGITALOCEAN))
  }

  @Test
  fun testDropboxIssuer() {
    val issuer = issuerDecoder.decode("", "dropbox")

    assertThat(issuer, `is`(Issuer.DROPBOX))
  }

  @Test
  fun testEvernoteIssuer() {
    val issuer = issuerDecoder.decode("", "evernote")

    assertThat(issuer, `is`(Issuer.EVERNOTE))
  }

  @Test
  fun testFacebookIssuer() {
    val issuer = issuerDecoder.decode("", "facebook")

    assertThat(issuer, `is`(Issuer.FACEBOOK))
  }

  @Test
  fun testGitHubIssuer() {
    val issuer = issuerDecoder.decode("", "github")

    assertThat(issuer, `is`(Issuer.GITHUB))
  }

  @Test
  fun testGoogleIssuer() {
    val issuer = issuerDecoder.decode("", "google")

    assertThat(issuer, `is`(Issuer.GOOGLE))
  }

  @Test
  fun testMicrosoftIssuer() {
    val issuer = issuerDecoder.decode("", "microsoft")

    assertThat(issuer, `is`(Issuer.MICROSOFT))
  }

  @Test
  fun testSlackIssuer() {
    val issuer = issuerDecoder.decode("", "slack")

    assertThat(issuer, `is`(Issuer.SLACK))
  }

  @Test
  fun testWordpressIssuer() {
    val issuer = issuerDecoder.decode("", "wordpress")

    assertThat(issuer, `is`(Issuer.WORDPRESS))
  }

  @Test
  fun testRobertsSpaceIndustriesIssuer() {
    val issuerWithSpaces = issuerDecoder.decode("", "Roberts Space Industries")
    val issuerWithOneSpace = issuerDecoder.decode("", "Roberts Space Industries")
    val issuerWithAnotherSpace = issuerDecoder.decode("", "Roberts Space Industries")
    val issuerWithoutSpaces = issuerDecoder.decode("", "Roberts Space Industries")

    assertThat(issuerWithSpaces, `is`(Issuer.ROBERTSPACEINDUSTRIES))
    assertThat(issuerWithOneSpace, `is`(Issuer.ROBERTSPACEINDUSTRIES))
    assertThat(issuerWithAnotherSpace, `is`(Issuer.ROBERTSPACEINDUSTRIES))
    assertThat(issuerWithoutSpaces, `is`(Issuer.ROBERTSPACEINDUSTRIES))
  }
}
