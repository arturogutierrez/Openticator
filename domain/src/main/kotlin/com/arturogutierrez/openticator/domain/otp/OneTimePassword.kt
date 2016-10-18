package com.arturogutierrez.openticator.domain.otp

import com.arturogutierrez.openticator.domain.otp.model.Passcode

interface OneTimePassword {
    fun generate(): Passcode
}
