package com.arturogutierrez.openticator.domain.account.repository

import com.arturogutierrez.openticator.ApplicationTestCase
import com.arturogutierrez.openticator.domain.account.model.Account
import com.arturogutierrez.openticator.domain.account.model.OTPType
import com.arturogutierrez.openticator.domain.issuer.model.Issuer
import com.arturogutierrez.openticator.storage.AccountDiskDataStore
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

import org.mockito.Mockito.verify

class AccountRepositoryTest : ApplicationTestCase() {

    private lateinit var accountRepository: AccountRepository
    @Mock
    private lateinit var accountDiskDataStore: AccountDiskDataStore

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        accountRepository = AccountRepositoryImpl(accountDiskDataStore)
    }

    @Test
    fun testAddAccount() {
        val account = Account("id", "name", OTPType.TOTP, "secret", Issuer.UNKNOWN)

        accountRepository.add(account)

        verify<AccountDiskDataStore>(accountDiskDataStore).add(account)
    }

    @Test
    fun testGetAccounts() {
        accountRepository.allAccounts

        verify<AccountDiskDataStore>(accountDiskDataStore).allAccounts
    }
}
