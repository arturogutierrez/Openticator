package com.arturogutierrez.openticator.domain.otp.time

import com.arturogutierrez.openticator.domain.otp.stubs.StubTimeProvider
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class RemainingTimeCalculatorTest {

  @Test
  fun testCalculateRemainingSeconds() {
    val timeProvider = StubTimeProvider(100L)
    val remainingTimeCalculator = RemainingTimeCalculator(timeProvider)

    val remainingSeconds = remainingTimeCalculator.calculateRemainingSeconds(120L)

    assertThat(remainingSeconds, equalTo(20L))
  }

  @Test
  fun testRemainingSecondsShouldBeZeroIfCurrentTimeIsAfterValidUntilDate() {
    val timeProvider = StubTimeProvider(50L)
    val remainingTimeCalculator = RemainingTimeCalculator(timeProvider)

    val remainingSeconds = remainingTimeCalculator.calculateRemainingSeconds(20L)

    assertThat(remainingSeconds, equalTo(0L))
  }
}
