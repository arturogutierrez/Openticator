package com.arturogutierrez.openticator.domain.account

import com.arturogutierrez.openticator.domain.account.model.Account
import com.arturogutierrez.openticator.domain.account.model.OTPType
import com.arturogutierrez.openticator.domain.issuer.IssuerDecoder
import com.arturogutierrez.openticator.domain.issuer.model.Issuer
import java.util.*
import javax.inject.Inject

open class AccountFactory @Inject constructor(val issuerDecoder: IssuerDecoder) {

    open fun createAccount(name: String, secret: String): Account {
        return createAccount(OTPType.TOTP, name, secret)
    }

    fun createAccount(otpType: OTPType, name: String, secret: String, issuer: String? = Issuer.UNKNOWN.identifier): Account {
        val accountId = UUID.randomUUID().toString()
        val decodedIssuer = issuerDecoder.decode(name, issuer ?: Issuer.UNKNOWN.identifier)
        return Account(accountId, name, otpType, secret, decodedIssuer)
    }

    fun createAccount(account: Account, newName: String): Account {
        return Account(account.accountId, newName, account.type, account.secret,
                account.issuer, account.order)
    }

    fun createAccount(account: Account, newIssuer: Issuer): Account {
        return Account(account.accountId, account.name, account.type,
                account.secret, newIssuer, account.order)
    }
}
