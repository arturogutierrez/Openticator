package com.arturogutierrez.openticator.domain.otp

import com.arturogutierrez.openticator.domain.account.model.Account
import com.arturogutierrez.openticator.domain.account.model.OTPType
import com.arturogutierrez.openticator.domain.otp.time.CurrentTimeProvider
import com.arturogutierrez.openticator.domain.otp.time.TimeCalculator
import javax.inject.Inject

class OneTimePasswordFactory @Inject constructor() {

    companion object {
        private val DEFAULT_TIME_STEP_LENGTH = 30
        private val DEFAULT_PASSWORD_LENGTH = 6
    }

    fun createOneTimePassword(account: Account, timeCorrectionInMinutes: Int): OneTimePassword {
        if (account.type === OTPType.TOTP) {
            return createTimeBasedOneTimePassword(account, timeCorrectionInMinutes)
        }

        throw UnsupportedOperationException("HTOP support not implemented yet")
    }

    private fun createTimeBasedOneTimePassword(account: Account,
                                               timeCorrectionInMinutes: Int): OneTimePassword {
        val oneTimePasswordGenerator = OneTimePasswordGenerator(account.secret, DEFAULT_PASSWORD_LENGTH)
        val timeProvider = CurrentTimeProvider()
        val timeCalculator = TimeCalculator(DEFAULT_TIME_STEP_LENGTH, timeCorrectionInMinutes)
        return TimeBasedOneTimePassword(timeProvider, oneTimePasswordGenerator, timeCalculator)
    }
}
