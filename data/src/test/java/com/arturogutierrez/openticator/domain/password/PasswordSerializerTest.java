package com.arturogutierrez.openticator.domain.password;

import com.arturogutierrez.openticator.ApplicationTestCase;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class PasswordSerializerTest extends ApplicationTestCase{

  private PasswordSerializer passwordSerializer;

  @Before
  public void setUp() {
    passwordSerializer = new PasswordSerializerImpl();
  }

  @Test
  public void testNullPasswordReturnNullSerialization() {
    String encodedPassword = passwordSerializer.encodePassword(null);

    assertThat(encodedPassword, is(nullValue()));
  }

  @Test
  public void testEmptyPasswordReturnSerialization() {
    String encodedPassword = passwordSerializer.encodePassword("");

    assertThat(encodedPassword, is("47DEQpj8HBSa+/TImW+5JCeuQeRkm5NMpJWZG3hSuFU="));
  }

  @Test
  public void testSerializationWithSHA256() {
    String encodedPassword = passwordSerializer.encodePassword("password");

    assertThat(encodedPassword, is("XohImNooBHFR0OVvjcYpJ3NgPQ1qq73WKhHvch0VQtg="));
  }
}
