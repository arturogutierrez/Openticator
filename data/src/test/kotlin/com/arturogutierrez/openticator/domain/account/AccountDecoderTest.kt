package com.arturogutierrez.openticator.domain.account

import com.arturogutierrez.openticator.ApplicationTestCase
import com.arturogutierrez.openticator.domain.account.model.OTPType
import com.arturogutierrez.openticator.domain.issuer.IssuerDecoder
import com.arturogutierrez.openticator.domain.issuer.model.Issuer
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsNull.notNullValue
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

class AccountDecoderTest : ApplicationTestCase() {

    private lateinit var issuerDecoder: IssuerDecoder
    private lateinit var accountFactory: AccountFactory
    private lateinit var accountDecoder: AccountDecoder

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        issuerDecoder = IssuerDecoder()
        accountFactory = AccountFactory(issuerDecoder)
        accountDecoder = AccountDecoder(accountFactory)
    }

    @Test
    fun testHOTPType() {
        val accountUri = "otpauth://hotp/a:b?secret=SECRET"

        val account = accountDecoder.decode(accountUri)

        assertThat(account, `is`(notNullValue()))
        assertThat(account.type, `is`(OTPType.HOTP))
    }

    @Test
    fun testTOTPType() {
        val accountUri = "otpauth://totp/Openticator:tony.stark@starkindustries.com?secret=SECRET"

        val account = accountDecoder.decode(accountUri)

        assertThat(account, `is`(notNullValue()))
        assertThat(account.type, `is`(OTPType.TOTP))
    }

    @Test
    fun testAccountNameIfThereIsAPair() {
        val accountUri = "otpauth://totp/Openticator:tony.stark@starkindustries.com?secret=SECRET"

        val account = accountDecoder.decode(accountUri)

        assertThat(account, `is`(notNullValue()))
        assertThat(account.type, `is`(OTPType.TOTP))
        assertThat(account.name, `is`("tony.stark@starkindustries.com"))
    }

    @Test
    fun testAccountNameWithNoPair() {
        val accountUri = "otpauth://totp/tony.stark@starkindustries.com?secret=SECRET"

        val account = accountDecoder.decode(accountUri)

        assertThat(account, `is`(notNullValue()))
        assertThat(account.type, `is`(OTPType.TOTP))
        assertThat(account.name, `is`("tony.stark@starkindustries.com"))
    }

    @Test
    fun testSecret() {
        val accountUri = "otpauth://totp/tony.stark@starkindustries.com?secret=SECRET"

        val account = accountDecoder.decode(accountUri)

        assertThat(account, `is`(notNullValue()))
        assertThat(account.type, `is`(OTPType.TOTP))
        assertThat(account.secret, `is`("SECRET"))
    }

    @Test
    fun testOpenticator() {
        val accountUri = "otpauth://totp/tony.stark@starkindustries.com?secret=SECRET&issuer=OPENTICATOR"

        val account = accountDecoder.decode(accountUri)

        assertThat(account, `is`(notNullValue()))
        assertThat(account.type, `is`(OTPType.TOTP))
        assertThat(account.issuer, `is`(Issuer.UNKNOWN))
    }

    @Test
    fun testDecodingHappyCase() {
        val accountUri = "otpauth://totp/Openticator:tony.stark@starkindustries.com?secret=ABCDEFGHASD&issuer=Openticator"

        val account = accountDecoder.decode(accountUri)

        assertThat(account, `is`(notNullValue()))
        assertThat(account.type, `is`(OTPType.TOTP))
        assertThat(account.name, `is`("tony.stark@starkindustries.com"))
        assertThat(account.secret, `is`("ABCDEFGHASD"))
        assertThat(account.issuer, `is`(Issuer.UNKNOWN))
    }

    @Test
    fun testDecodingHappyCaseURLEncoded() {
        val accountUri = "otpauth://totp/Openticator%3Atony.stark%40starkindustries.com?secret=ABCDEFGHASD&issuer=Openticator"

        val account = accountDecoder.decode(accountUri)

        assertThat(account, `is`(notNullValue()))
        assertThat(account.type, `is`(OTPType.TOTP))
        assertThat(account.name, `is`("tony.stark@starkindustries.com"))
        assertThat(account.secret, `is`("ABCDEFGHASD"))
        assertThat(account.issuer, `is`(Issuer.UNKNOWN))
    }
}
