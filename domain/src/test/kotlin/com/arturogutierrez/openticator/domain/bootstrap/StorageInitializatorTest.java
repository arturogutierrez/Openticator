package com.arturogutierrez.openticator.domain.bootstrap;

import com.arturogutierrez.openticator.domain.DatabaseConfigurator;
import com.arturogutierrez.openticator.domain.Preferences;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StorageInitializatorTest {
  @Mock
  private Preferences mockPreferences;
  @Mock
  private DatabaseConfigurator mockDatabaseConfigurator;

  private StorageInitializator storageInitializator;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);

    storageInitializator = new StorageInitializator(mockPreferences, mockDatabaseConfigurator);
  }

  @Test
  public void testInitializingDatabaseWithPasswordFromPreferences() {
    when(mockPreferences.getMasterPassword()).thenReturn("password");

    storageInitializator.configureStorage();

    verify(mockDatabaseConfigurator).configure("password");
  }
}
