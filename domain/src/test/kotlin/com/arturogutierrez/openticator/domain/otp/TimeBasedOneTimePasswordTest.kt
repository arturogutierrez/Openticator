package com.arturogutierrez.openticator.domain.otp

import com.arturogutierrez.openticator.domain.otp.stubs.StubTimeProvider
import com.arturogutierrez.openticator.domain.otp.time.TimeCalculator
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Before
import org.junit.Test

class TimeBasedOneTimePasswordTest {

    private lateinit var oneTimePasswordGenerator: OneTimePasswordGenerator
    private lateinit var timeCalculator: TimeCalculator

    @Before
    fun setUp() {
        oneTimePasswordGenerator = OneTimePasswordGenerator("ABCDEFGHIJK23456", 6)
        timeCalculator = TimeCalculator(TIME_STEP_LENGTH, 0)
    }

    @Test
    fun testCodeForFirstStep() {
        val timeProvider = StubTimeProvider(TIME_STEP_LENGTH.toLong())
        val timeBasedOneTimePassword = TimeBasedOneTimePassword(timeProvider, oneTimePasswordGenerator!!, timeCalculator!!)

        val passcode = timeBasedOneTimePassword.generate()

        assertThat(passcode.code, equalTo("784240"))
    }

    @Test
    fun testCodeForSecondStep() {
        val timeProvider = StubTimeProvider((TIME_STEP_LENGTH * 2).toLong())
        val timeBasedOneTimePassword = TimeBasedOneTimePassword(timeProvider, oneTimePasswordGenerator!!, timeCalculator!!)

        val passcode = timeBasedOneTimePassword.generate()

        assertThat(passcode.code, equalTo("388205"))
    }

    companion object {

        private val TIME_STEP_LENGTH = 30
    }
}
