package com.arturogutierrez.openticator.domain.otp;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class OneTimePasswordGeneratorTests {

  private OneTimePasswordFactory oneTimePasswordFactory;

  @Before
  public void setUp() {
    oneTimePasswordFactory = new OneTimePasswordFactory();
  }

  @Test
  public void testGenerateCodeForFirstState() {
    String secret = "7777777777777777";
    OneTimePasswordGenerator oneTimePasswordGenerator = new OneTimePasswordGenerator(secret);

    String code = oneTimePasswordGenerator.generate(1);

    assertThat(code, is(683298));
  }
}
