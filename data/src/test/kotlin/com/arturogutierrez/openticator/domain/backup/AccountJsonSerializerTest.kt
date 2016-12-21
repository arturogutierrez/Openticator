package com.arturogutierrez.openticator.domain.backup

import com.arturogutierrez.openticator.domain.account.model.Account
import com.arturogutierrez.openticator.domain.account.model.OTPType
import com.arturogutierrez.openticator.domain.category.model.Category
import com.arturogutierrez.openticator.domain.issuer.model.Issuer
import com.arturogutierrez.openticator.storage.json.mapper.AccountEntityMapper
import com.arturogutierrez.openticator.storage.json.mapper.OTPTypeMapper
import com.google.gson.Gson
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.runners.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AccountJsonSerializerTest {

  companion object Defaults {
    val ID = "id"
    val NAME = "account"
    val SECRET = "bestSecret"
    val TYPE = OTPType.TOTP
    val ISSUER = Issuer.GOOGLE
    val ORDER = 5
  }

  private lateinit var accountJsonSerializer: AccountJsonSerializer

  @Before
  fun setUp() {
    val otpTypeMapper = OTPTypeMapper()
    val accountEntityMapper = AccountEntityMapper(otpTypeMapper)
    val gson = Gson()
    accountJsonSerializer = AccountJsonSerializer(accountEntityMapper, gson)
  }

  @Test
  fun shouldSerializeOneAccount() {
    val category = Category(ID, NAME)
    val account = Account(ID, NAME, TYPE, SECRET, ISSUER, ORDER)

    val json = accountJsonSerializer.serialize(listOf(Pair(account, category)))

    assertThat(json, `is`("[{\"id\":\"id\",\"name\":\"account\",\"type\":\"TOTP\"," +
        "\"secret\":\"bestSecret\",\"issuer\":\"google\",\"order\":5,\"category_id\":\"id\"}]"))
  }

  @Test
  fun shouldSerializeSeveralAccounts() {
    val firstCategory = Category(ID, NAME)
    val secondCategory = Category(ID, NAME)
    val firstAccount = Account(ID, NAME, TYPE, SECRET, ISSUER, ORDER)
    val secondAccount = Account(ID, NAME, TYPE, SECRET, ISSUER, ORDER)

    val json = accountJsonSerializer.serialize(listOf(Pair(firstAccount, firstCategory),
        Pair(secondAccount, secondCategory)))

    assertThat(json, `is`("[{\"id\":\"id\",\"name\":\"account\",\"type\":\"TOTP\"," +
        "\"secret\":\"bestSecret\",\"issuer\":\"google\",\"order\":5,\"category_id\":\"id\"}," +
        "{\"id\":\"id\",\"name\":\"account\",\"type\":\"TOTP\",\"secret\":\"bestSecret\"," +
        "\"issuer\":\"google\",\"order\":5,\"category_id\":\"id\"}]"))
  }
}
