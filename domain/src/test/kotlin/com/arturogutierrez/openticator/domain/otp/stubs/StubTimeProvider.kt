package com.arturogutierrez.openticator.domain.otp.stubs

import com.arturogutierrez.openticator.domain.otp.time.TimeProvider

class StubTimeProvider constructor(val currentTime: Long) : TimeProvider {
    override fun currentTimeInSeconds(): Long {
        return currentTime
    }
}
