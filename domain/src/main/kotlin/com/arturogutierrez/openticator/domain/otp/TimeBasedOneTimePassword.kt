package com.arturogutierrez.openticator.domain.otp

import com.arturogutierrez.openticator.domain.otp.model.Passcode
import com.arturogutierrez.openticator.domain.otp.time.TimeCalculator
import com.arturogutierrez.openticator.domain.otp.time.TimeProvider

internal class TimeBasedOneTimePassword(private val currentTimeProvider: TimeProvider,
                                        private val oneTimePasswordGenerator: OneTimePasswordGenerator,
                                        private val timeCalculator: TimeCalculator) : OneTimePassword {

    override fun generate(): Passcode {
        val currentTimeInSeconds = currentTimeProvider.currentTimeInSeconds()
        val currentStep = timeCalculator.getCurrentTimeStep(currentTimeInSeconds)
        val validUntilInSeconds = timeCalculator.getValidUntilInSeconds(currentStep)
        val code = oneTimePasswordGenerator.generate(currentStep)
        return Passcode(code, validUntilInSeconds)
    }
}
