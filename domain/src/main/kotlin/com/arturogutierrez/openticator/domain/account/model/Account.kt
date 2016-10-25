package com.arturogutierrez.openticator.domain.account.model

import com.arturogutierrez.openticator.domain.issuer.model.Issuer

data class Account(val accountId: String, val name: String,
                   val type: OTPType, val secret: String,
                   val issuer: Issuer, val order: Int = Int.MAX_VALUE)
