package com.arturogutierrez.openticator.domain.otp.time

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test


class TimeCalculatorTest {

  @Test
  fun testFirstStep() {
    val timeCalculator = TimeCalculator(30, 0)

    val step = timeCalculator.getCurrentTimeStep(28L)

    assertThat(step, equalTo(0L))
  }

  @Test
  fun testSecondStep() {
    val timeCalculator = TimeCalculator(30, 0)

    val step = timeCalculator.getCurrentTimeStep(50L)

    assertThat(step, equalTo(1L))
  }

  @Test
  fun testTimeStepUsingCorrection() {
    val timeCalculator = TimeCalculator(30, -15)

    val step = timeCalculator.getCurrentTimeStep(65L)

    assertThat(step, equalTo(1L))
  }

  @Test
  fun testValidTimeStep() {
    val timeCalculator = TimeCalculator(30, 0)

    val step = timeCalculator.getCurrentTimeStep(65L)
    val validUntilInSeconds = timeCalculator.getValidUntilInSeconds(step)

    assertThat(step, equalTo(2L))
    assertThat(validUntilInSeconds, equalTo(90L))
  }
}
