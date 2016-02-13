package com.arturogutierrez.openticator.domain.password.interactor;

import com.arturogutierrez.openticator.domain.Preferences;
import com.arturogutierrez.openticator.domain.password.PasswordSerializer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CreateMasterPasswordInteractorTest {

  @Mock
  private Preferences mockPreferences;
  @Mock
  private PasswordSerializer mockPasswordSerializer;

  private CreateMasterPasswordInteractor createMasterPasswordInteractor;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);

    createMasterPasswordInteractor =
        new CreateMasterPasswordInteractor(mockPreferences, mockPasswordSerializer);
  }

  @Test
  public void testCreateMasterPasswordIsUsingPasswordSerializer() {
    createMasterPasswordInteractor.createMasterPassword("password");

    verify(mockPasswordSerializer).encodePassword("password");
  }

  @Test
  public void testCreateMasterPasswordIsSavingTheHashInPreferences() {
    when(mockPasswordSerializer.encodePassword(anyString())).thenReturn("hash");

    createMasterPasswordInteractor.createMasterPassword("password");

    verify(mockPreferences).setMasterPassword("hash");
  }

  @Test
  public void testUsingPlainPasswordIfThereIsNoAlgorithm() {
    createMasterPasswordInteractor.createMasterPassword("password");

    verify(mockPreferences).setMasterPassword("password");
  }
}
