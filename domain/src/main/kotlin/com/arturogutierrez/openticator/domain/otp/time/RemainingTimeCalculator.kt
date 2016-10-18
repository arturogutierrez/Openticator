package com.arturogutierrez.openticator.domain.otp.time

import javax.inject.Inject

class RemainingTimeCalculator @Inject constructor(val timeProvider: CurrentTimeProvider) {

    fun calculateRemainingSeconds(validUntilInSeconds: Long): Int {
        val remainingSeconds = (validUntilInSeconds - timeProvider.currentTimeInSeconds()).toInt()
        if (remainingSeconds < 0) {
            return 0
        }

        return remainingSeconds
    }
}
