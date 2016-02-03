package com.arturogutierrez.openticator.domain.otp;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class OneTimePasswordGeneratorTests {

  @Test
  public void testGenerateCodeForFirstState() {
    String secret = "ABCDEFGHIJK23456";
    OneTimePasswordGenerator oneTimePasswordGenerator = new OneTimePasswordGenerator(secret, 6);

    String code = oneTimePasswordGenerator.generate(0);

    assertThat(code, is("867671"));
  }
}
