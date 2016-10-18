package com.arturogutierrez.openticator.domain.otp;

import com.arturogutierrez.openticator.domain.otp.model.Passcode;
import com.arturogutierrez.openticator.domain.otp.time.StubTimeProvider;
import com.arturogutierrez.openticator.domain.otp.time.TimeCalculator;
import com.arturogutierrez.openticator.domain.otp.time.TimeProvider;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

public class TimeBasedOneTimePasswordTest {

  private static int TIME_STEP_LENGTH = 30;

  private OneTimePasswordGenerator oneTimePasswordGenerator;
  private TimeCalculator timeCalculator;

  @Before
  public void setUp() {
    oneTimePasswordGenerator = new OneTimePasswordGenerator("ABCDEFGHIJK23456", 6);
    timeCalculator = new TimeCalculator(TIME_STEP_LENGTH, 0);
  }

  @Test
  public void testCodeForFirstStep() {
    TimeProvider timeProvider = new StubTimeProvider(TIME_STEP_LENGTH);
    TimeBasedOneTimePassword timeBasedOneTimePassword =
        new TimeBasedOneTimePassword(timeProvider, oneTimePasswordGenerator, timeCalculator);

    Passcode passcode = timeBasedOneTimePassword.generate();

    assertThat(passcode.getCode(), is("784240"));
  }

  @Test
  public void testCodeForSecondStep() {
    TimeProvider timeProvider = new StubTimeProvider(TIME_STEP_LENGTH * 2);
    TimeBasedOneTimePassword timeBasedOneTimePassword =
        new TimeBasedOneTimePassword(timeProvider, oneTimePasswordGenerator, timeCalculator);

    Passcode passcode = timeBasedOneTimePassword.generate();

    assertThat(passcode.getCode(), is("388205"));
  }
}
