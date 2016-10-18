package com.arturogutierrez.openticator.domain.password;

import android.util.Base64;
import com.arturogutierrez.openticator.ApplicationTestCase;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PasswordSerializerTest extends ApplicationTestCase {

  private PasswordSerializer passwordSerializer;

  @Before
  public void setUp() {
    passwordSerializer = new PasswordSerializerImpl();
  }

  @Test
  public void testEmptyPasswordReturnSerialization() {
    String encodedPassword = passwordSerializer.encodePassword("");

    assertThat(encodedPassword,
        is("z4PhNX7vuL3xVChQ1m2AB9Yg5AULVxXcg/SpIdNs6c5H0NE8XYXysP+DGNKHfuwvY7kxvUdBeoGlODJ6+SfaPg=="));
  }

  @Test
  public void testSerializationWithSHA512Returns64Bytes() {
    String encodedPasswordInBase64 = passwordSerializer.encodePassword("password");
    byte[] decodedPassword = Base64.decode(encodedPasswordInBase64, Base64.DEFAULT);

    assertThat(decodedPassword.length, is(64));
  }
}
