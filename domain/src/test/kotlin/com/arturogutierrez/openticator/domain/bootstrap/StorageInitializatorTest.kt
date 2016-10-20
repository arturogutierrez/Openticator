package com.arturogutierrez.openticator.domain.bootstrap

import com.arturogutierrez.openticator.domain.DatabaseConfigurator
import com.arturogutierrez.openticator.domain.Preferences
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class StorageInitializatorTest {
  @Mock
  private lateinit var mockPreferences: Preferences
  @Mock
  private lateinit var mockDatabaseConfigurator: DatabaseConfigurator

  private lateinit var storageInitializator: StorageInitializator

  @Before
  fun setUp() {
    MockitoAnnotations.initMocks(this)

    storageInitializator = StorageInitializator(mockPreferences, mockDatabaseConfigurator)
  }

  @Test
  fun testInitializingDatabaseWithPasswordFromPreferences() {
    `when`<String>(mockPreferences.masterPassword).thenReturn("password")

    storageInitializator.configureStorage()

    verify<DatabaseConfigurator>(mockDatabaseConfigurator).configure("password")
  }
}
