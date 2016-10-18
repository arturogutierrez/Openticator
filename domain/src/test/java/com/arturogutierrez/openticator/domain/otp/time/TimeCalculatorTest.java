package com.arturogutierrez.openticator.domain.otp.time;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class TimeCalculatorTest {

  @Test
  public void testFirstStep() {
    TimeCalculator timeCalculator = new TimeCalculator(30, 0);

    long step = timeCalculator.getCurrentTimeStep(28L);

    assertThat(step, is(0L));
  }

  @Test
  public void testSecondStep() {
    TimeCalculator timeCalculator = new TimeCalculator(30, 0);

    long step = timeCalculator.getCurrentTimeStep(50L);

    assertThat(step, is(1L));
  }

  @Test
  public void testTimeStepUsingCorrection() {
    TimeCalculator timeCalculator = new TimeCalculator(30, -15);

    long step = timeCalculator.getCurrentTimeStep(65L);

    assertThat(step, is(1L));
  }

  @Test
  public void testValidTimeStep() {
    TimeCalculator timeCalculator = new TimeCalculator(30, 0);

    long step = timeCalculator.getCurrentTimeStep(65L);
    long validUntilInSeconds = timeCalculator.getValidUntilInSeconds(step);

    assertThat(step, is(2L));
    assertThat(validUntilInSeconds, is(90L));
  }
}
