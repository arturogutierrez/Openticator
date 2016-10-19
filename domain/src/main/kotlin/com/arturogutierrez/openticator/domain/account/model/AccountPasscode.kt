package com.arturogutierrez.openticator.domain.account.model

import com.arturogutierrez.openticator.domain.issuer.model.Issuer
import com.arturogutierrez.openticator.domain.otp.model.Passcode

data class AccountPasscode(val account: Account, val issuer: Issuer, val passcode: Passcode)
