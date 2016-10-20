package com.arturogutierrez.openticator.domain.otp.time

class TimeCalculator(private val timeStepLengthInSeconds: Int,
                     private val timeCorrectionInSeconds: Int) {

  fun getCurrentTimeStep(currentTimeInSeconds: Long): Long {
    return (currentTimeInSeconds + timeCorrectionInSeconds) / timeStepLengthInSeconds
  }

  fun getValidUntilInSeconds(timeStep: Long): Long {
    return (timeStep + 1) * timeStepLengthInSeconds - timeCorrectionInSeconds
  }
}
