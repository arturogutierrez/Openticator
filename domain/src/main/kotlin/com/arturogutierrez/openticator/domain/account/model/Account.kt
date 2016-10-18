package com.arturogutierrez.openticator.domain.account.model

import com.arturogutierrez.openticator.domain.issuer.model.Issuer

data class Account @JvmOverloads constructor(val accountId: String, val name: String,
                                             val type: OTPType, val secret: String,
                                             val issuer: Issuer, val order: Int = maxOrder) {

    companion object {
        val maxOrder = Int.MAX_VALUE
    }

    override fun equals(other: Any?): Boolean {
        if (other is Account) {
            return accountId == other.accountId
        }
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return accountId.hashCode()
    }
}
