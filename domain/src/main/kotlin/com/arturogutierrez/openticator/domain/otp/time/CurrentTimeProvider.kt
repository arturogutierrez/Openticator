package com.arturogutierrez.openticator.domain.otp.time

import javax.inject.Inject

class CurrentTimeProvider @Inject constructor() : TimeProvider {
  override fun currentTimeInSeconds(): Long {
    return System.currentTimeMillis() / 1000
  }
}
