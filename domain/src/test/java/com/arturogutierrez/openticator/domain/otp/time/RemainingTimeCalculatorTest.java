package com.arturogutierrez.openticator.domain.otp.time;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

public class RemainingTimeCalculatorTest {

  @Test
  public void testCalculateRemainingSeconds() {
    TimeProvider timeProvider = new StubTimeProvider(100L);
    RemainingTimeCalculator remainingTimeCalculator = new RemainingTimeCalculator(timeProvider);

    int remainingSeconds = remainingTimeCalculator.calculateRemainingSeconds(120L);

    assertThat(remainingSeconds, is(20));
  }

  @Test
  public void testRemainingSecondsShouldBeZeroIfCurrentTimeIsAfterValidUntilDate() {
    TimeProvider timeProvider = new StubTimeProvider(50L);
    RemainingTimeCalculator remainingTimeCalculator = new RemainingTimeCalculator(timeProvider);

    int remainingSeconds = remainingTimeCalculator.calculateRemainingSeconds(20L);

    assertThat(remainingSeconds, is(0));
  }
}
