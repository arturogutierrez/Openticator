package com.arturogutierrez.openticator.domain.otp.time

import javax.inject.Inject

class RemainingTimeCalculator @Inject constructor(private val timeProvider: TimeProvider) {

  fun calculateRemainingSeconds(validUntilInSeconds: Long): Long {
    val remainingSeconds = (validUntilInSeconds - timeProvider.currentTimeInSeconds()).toInt()
    if (remainingSeconds < 0) {
      return 0
    }

    return remainingSeconds.toLong()
  }
}
