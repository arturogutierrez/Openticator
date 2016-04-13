package com.arturogutierrez.openticator.domain.backup;

import com.arturogutierrez.openticator.ApplicationTestCase;
import com.arturogutierrez.openticator.domain.backup.exceptions.EncryptionException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class JSONEncryptorTest extends ApplicationTestCase {

  private JSONEncryptor jsonEncryptor;

  @Before
  public void setUp() {
    jsonEncryptor = new JSONEncryptorImpl();
  }

  @Test
  @Ignore("The encryption algorithm is not available in Roboelectric")
  public void testEncryptingAndDecrypt() throws EncryptionException {
    String password = "password";
    String sourceText = "Avengers";

    String encryptedBase64Text = jsonEncryptor.encryptJSON(sourceText, password);
    String decryptedPlainText = jsonEncryptor.decryptJSON(encryptedBase64Text, password);

    assertThat(decryptedPlainText, is(sourceText));
  }
}
