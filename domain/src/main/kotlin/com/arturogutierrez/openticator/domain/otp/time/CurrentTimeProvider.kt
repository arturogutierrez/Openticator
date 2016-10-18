package com.arturogutierrez.openticator.domain.otp.time

internal class CurrentTimeProvider : TimeProvider {
    override fun currentTimeInSeconds(): Long {
        return System.currentTimeMillis() / 1000
    }
}
