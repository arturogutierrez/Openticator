package com.arturogutierrez.openticator.domain.otp.time

class StubTimeProvider constructor(val currentTime: Long) : TimeProvider {
    override fun currentTimeInSeconds(): Long {
        return currentTime
    }
}
