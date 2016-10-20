package com.arturogutierrez.openticator.domain.account

import com.arturogutierrez.openticator.domain.account.model.OTPType
import com.arturogutierrez.openticator.domain.issuer.IssuerDecoder
import com.arturogutierrez.openticator.domain.issuer.model.Issuer
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class AccountFactoryTest {

  @Mock
  private lateinit var mockIssuerDecoder: IssuerDecoder
  private lateinit var accountFactory: AccountFactory

  @Before
  fun setUp() {
    MockitoAnnotations.initMocks(this)
    accountFactory = AccountFactory(mockIssuerDecoder)
  }

  @Test
  fun testTryingToDecodeIssuer() {
    `when`(mockIssuerDecoder.decode(anyString(), anyString())).thenReturn(Issuer.GOOGLE)

    accountFactory.createAccount(OTPType.TOTP, "name", "secret", "issuer")

    verify(mockIssuerDecoder).decode("name", "issuer")
  }

  @Test
  fun testCreateAccount() {
    `when`(mockIssuerDecoder.decode(anyString(), anyString())).thenReturn(Issuer.UNKNOWN)

    val account = accountFactory.createAccount("name", "secret")

    assertThat(account.accountId, `is`(notNullValue()))
    assertThat(account.name, `is`("name"))
    assertThat(account.secret, `is`("secret"))
    assertThat(account.type, `is`(OTPType.TOTP))
    assertThat(account.issuer, `is`(Issuer.UNKNOWN))
    assertThat(account.order, `is`(Integer.MAX_VALUE))
  }
}
