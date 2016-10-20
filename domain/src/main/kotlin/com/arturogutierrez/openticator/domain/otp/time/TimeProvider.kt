package com.arturogutierrez.openticator.domain.otp.time

interface TimeProvider {

  fun currentTimeInSeconds(): Long

}