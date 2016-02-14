package com.arturogutierrez.openticator.domain.otp.time;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

public class RemainingTimeCalculatorTest {

  @Mock
  private CurrentTimeProvider mockCurrentTimeProvider;

  private RemainingTimeCalculator remainingTimeCalculator;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);

    remainingTimeCalculator = new RemainingTimeCalculator(mockCurrentTimeProvider);
  }

  @Test
  public void testCalculateRemainingSeconds() {
    when(mockCurrentTimeProvider.getCurrentTimeInSeconds()).thenReturn(100L);

    int remainingSeconds = remainingTimeCalculator.calculateRemainingSeconds(120L);

    assertThat(remainingSeconds, is(20));
  }

  @Test
  public void testRemainingSecondsShouldBeZeroIfCurrentTimeIsAfterValidUntilDate() {
    when(mockCurrentTimeProvider.getCurrentTimeInSeconds()).thenReturn(50L);

    int remainingSeconds = remainingTimeCalculator.calculateRemainingSeconds(20L);

    assertThat(remainingSeconds, is(0));
  }
}
