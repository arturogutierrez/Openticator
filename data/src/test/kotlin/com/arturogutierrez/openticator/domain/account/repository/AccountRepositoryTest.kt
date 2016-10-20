package com.arturogutierrez.openticator.domain.account.repository

import com.arturogutierrez.openticator.ApplicationTestCase
import com.arturogutierrez.openticator.domain.account.model.Account
import com.arturogutierrez.openticator.domain.account.model.OTPType
import com.arturogutierrez.openticator.domain.issuer.model.Issuer
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class AccountRepositoryTest : ApplicationTestCase() {

  private lateinit var accountRepository: AccountRepository
  @Mock
  private lateinit var mockAccountDataStore: AccountDataStore

  @Before
  fun setUp() {
    MockitoAnnotations.initMocks(this)

    accountRepository = AccountRepositoryImpl(mockAccountDataStore)
  }

  @Test
  fun testAddAccount() {
    val account = Account("id", "name", OTPType.TOTP, "secret", Issuer.UNKNOWN)

    accountRepository.add(account)

    verify<AccountDataStore>(mockAccountDataStore).add(account)
  }

  @Test
  fun testGetAccounts() {
    accountRepository.allAccounts

    verify<AccountDataStore>(mockAccountDataStore).allAccounts
  }
}
