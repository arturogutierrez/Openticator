package com.arturogutierrez.openticator.storage.realm.mapper

import com.arturogutierrez.openticator.ApplicationTestCase
import com.arturogutierrez.openticator.domain.account.model.Account
import com.arturogutierrez.openticator.domain.account.model.OTPType
import com.arturogutierrez.openticator.domain.issuer.model.Issuer
import com.arturogutierrez.openticator.storage.realm.model.AccountRealm
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

class AccountRealmMapperTest : ApplicationTestCase() {

  companion object {
    private val FAKE_ID = "1"
    private val FAKE_NAME = "tony@stark.com"
    private val FAKE_SECRET = "avengers"
    private val FAKE_ISSUER = Issuer.GOOGLE
    private val FAKE_ORDER = 1
  }

  private lateinit var accountRealmMapper: AccountRealmMapper

  @Before
  fun setUp() {
    accountRealmMapper = AccountRealmMapper()
  }

  @Test
  fun testAccountToAccountRealm() {
    val account = Account(FAKE_ID, FAKE_NAME, OTPType.TOTP, FAKE_SECRET, FAKE_ISSUER, FAKE_ORDER)

    val accountRealm = accountRealmMapper.transform(account)

    assertThat(accountRealm.accountId, `is`(FAKE_ID))
    assertThat(accountRealm.name, `is`(FAKE_NAME))
    assertThat(accountRealm.secret, `is`(FAKE_SECRET))
    assertThat(accountRealm.issuer, `is`(FAKE_ISSUER.identifier))
    assertThat(accountRealm.order, `is`(FAKE_ORDER))
  }

  @Test
  fun testHOTPTypeToRealm() {
    val account = Account(FAKE_ID, FAKE_NAME, OTPType.HOTP, FAKE_SECRET, FAKE_ISSUER, FAKE_ORDER)

    val accountRealm = accountRealmMapper.transform(account)

    assertThat(accountRealm.type, `is`(AccountRealm.HOTP_TYPE))
  }

  @Test
  fun testTOTPTypeToRealm() {
    val account = Account(FAKE_ID, FAKE_NAME, OTPType.TOTP, FAKE_SECRET, FAKE_ISSUER, FAKE_ORDER)

    val accountRealm = accountRealmMapper.transform(account)

    assertThat(accountRealm.type, `is`(AccountRealm.TOTP_TYPE))
  }

  @Test
  fun testAccountRealmToAccount() {
    val accountRealm = AccountRealm()
    accountRealm.accountId = FAKE_ID
    accountRealm.name = FAKE_NAME
    accountRealm.type = AccountRealm.TOTP_TYPE
    accountRealm.secret = FAKE_SECRET
    accountRealm.issuer = FAKE_ISSUER.identifier

    val account = accountRealmMapper.reverseTransform(accountRealm)

    assertThat(account.accountId, `is`(FAKE_ID))
    assertThat(account.name, `is`(FAKE_NAME))
    assertThat(account.secret, `is`(FAKE_SECRET))
    assertThat(account.issuer, `is`(FAKE_ISSUER))
  }

  @Test
  fun testHOTPRealmTypeToOTPType() {
    val accountRealm = AccountRealm()
    accountRealm.type = AccountRealm.HOTP_TYPE
    accountRealm.accountId = FAKE_ID
    accountRealm.name = FAKE_NAME
    accountRealm.secret = FAKE_SECRET
    accountRealm.issuer = FAKE_ISSUER.identifier

    val account = accountRealmMapper.reverseTransform(accountRealm)

    assertThat(account.type, `is`(OTPType.HOTP))
  }

  @Test
  fun testTOTPRealmTypeToOTPType() {
    val accountRealm = AccountRealm()
    accountRealm.type = AccountRealm.TOTP_TYPE
    accountRealm.accountId = FAKE_ID
    accountRealm.name = FAKE_NAME
    accountRealm.secret = FAKE_SECRET
    accountRealm.issuer = FAKE_ISSUER.identifier

    val account = accountRealmMapper.reverseTransform(accountRealm)

    assertThat(account.type, `is`(OTPType.TOTP))
  }
}