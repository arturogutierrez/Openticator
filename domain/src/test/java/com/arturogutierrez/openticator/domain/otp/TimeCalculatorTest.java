package com.arturogutierrez.openticator.domain.otp;

import com.arturogutierrez.openticator.domain.otp.time.TimeCalculator;
import com.arturogutierrez.openticator.domain.otp.time.TimeProvider;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class TimeCalculatorTest {

  @Mock
  private TimeProvider timeProvider;

  @Before
  public void setUp() {
    initMocks(this);
  }

  @Test
  public void testFirstStep() {
    when(timeProvider.getCurrentTimeInSeconds()).thenReturn(28L);
    TimeCalculator timeCalculator = new TimeCalculator(timeProvider, 30, 0);

    long step = timeCalculator.getCurrentTimeStep();

    assertThat(step, is(0L));
  }

  @Test
  public void testSecondStep() {
    when(timeProvider.getCurrentTimeInSeconds()).thenReturn(50L);
    TimeCalculator timeCalculator = new TimeCalculator(timeProvider, 40, 0);

    long step = timeCalculator.getCurrentTimeStep();

    assertThat(step, is(1L));
  }

  @Test
  public void testTimeStepUsingCorrection() {
    when(timeProvider.getCurrentTimeInSeconds()).thenReturn(65L);
    TimeCalculator timeCalculator = new TimeCalculator(timeProvider, 30, -15);

    long step = timeCalculator.getCurrentTimeStep();

    assertThat(step, is(1L));
  }
}
