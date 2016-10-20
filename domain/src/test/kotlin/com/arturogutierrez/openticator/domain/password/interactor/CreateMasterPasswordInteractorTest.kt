package com.arturogutierrez.openticator.domain.password.interactor

import com.arturogutierrez.openticator.domain.Preferences
import com.arturogutierrez.openticator.domain.password.PasswordSerializer
import org.junit.Before
import org.junit.Test
import org.mockito.Matchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class CreateMasterPasswordInteractorTest {

  @Mock
  private lateinit var mockPreferences: Preferences
  @Mock
  private lateinit var mockPasswordSerializer: PasswordSerializer

  private lateinit var createMasterPasswordInteractor: CreateMasterPasswordInteractor

  @Before
  fun setUp() {
    MockitoAnnotations.initMocks(this)

    createMasterPasswordInteractor = CreateMasterPasswordInteractor(mockPreferences, mockPasswordSerializer)
  }

  @Test
  fun testCreateMasterPasswordIsUsingPasswordSerializer() {
    createMasterPasswordInteractor.createMasterPassword("password")

    verify<PasswordSerializer>(mockPasswordSerializer).encodePassword("password")
  }

  @Test
  fun testCreateMasterPasswordIsSavingTheHashInPreferences() {
    `when`(mockPasswordSerializer.encodePassword(anyString())).thenReturn("hash")

    createMasterPasswordInteractor.createMasterPassword("password")

    verify<Preferences>(mockPreferences).masterPassword = "hash"
  }

  @Test
  fun testUsingPlainPasswordIfThereIsNoAlgorithm() {
    createMasterPasswordInteractor.createMasterPassword("password")

    verify<Preferences>(mockPreferences).masterPassword = "password"
  }
}
