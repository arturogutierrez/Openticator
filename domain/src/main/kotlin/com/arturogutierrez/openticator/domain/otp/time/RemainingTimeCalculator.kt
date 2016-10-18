package com.arturogutierrez.openticator.domain.otp.time

class RemainingTimeCalculator(private val timeProvider: TimeProvider) {

    fun calculateRemainingSeconds(validUntilInSeconds: Long): Int {
        val remainingSeconds = (validUntilInSeconds - timeProvider.currentTimeInSeconds()).toInt()
        if (remainingSeconds < 0) {
            return 0
        }

        return remainingSeconds
    }
}
