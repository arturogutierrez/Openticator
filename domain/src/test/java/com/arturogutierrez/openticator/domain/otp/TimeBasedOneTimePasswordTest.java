package com.arturogutierrez.openticator.domain.otp;

import com.arturogutierrez.openticator.domain.otp.time.TimeCalculator;
import com.arturogutierrez.openticator.domain.otp.time.TimeProvider;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

public class TimeBasedOneTimePasswordTest {

  private static int TIME_STEP_LENGTH = 30;

  private TimeBasedOneTimePassword timeBasedOneTimePassword;
  @Mock
  private TimeProvider timeProvider;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);

    OneTimePasswordGenerator oneTimePasswordGenerator =
        new OneTimePasswordGenerator("ABCDEFGHIJK23456", 6);
    TimeCalculator timeCalculator = new TimeCalculator(timeProvider, TIME_STEP_LENGTH, 0);
    timeBasedOneTimePassword =
        new TimeBasedOneTimePassword(oneTimePasswordGenerator, timeCalculator);
  }

  @Test
  public void testCodeForFirstStep() {
    when(timeProvider.getCurrentTimeInSeconds()).thenReturn((long) TIME_STEP_LENGTH);

    String code = timeBasedOneTimePassword.generate();

    assertThat(code, is("784240"));
  }

  @Test
  public void testCodeForSecondStep() {
    when(timeProvider.getCurrentTimeInSeconds()).thenReturn((long) TIME_STEP_LENGTH * 2);

    String code = timeBasedOneTimePassword.generate();

    assertThat(code, is("388205"));
  }
}
