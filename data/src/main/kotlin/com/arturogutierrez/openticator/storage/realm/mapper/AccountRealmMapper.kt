package com.arturogutierrez.openticator.storage.realm.mapper

import com.arturogutierrez.openticator.domain.account.model.Account
import com.arturogutierrez.openticator.domain.account.model.OTPType
import com.arturogutierrez.openticator.domain.issuer.model.Issuer
import com.arturogutierrez.openticator.mapper.Mapper
import com.arturogutierrez.openticator.storage.realm.model.AccountRealm
import javax.inject.Inject

class AccountRealmMapper @Inject constructor() : Mapper<Account, AccountRealm>() {

  override fun transform(value: Account): AccountRealm {
    val accountRealm = AccountRealm()
    copyToAccountRealm(accountRealm, value)
    return accountRealm
  }

  override fun reverseTransform(value: AccountRealm): Account {
    val issuer = transformIssuer(value.issuer)
    val account = Account(value.accountId, value.name,
        transformAccountType(value.type), value.secret, issuer,
        value.order)
    return account
  }

  fun copyToAccountRealm(accountRealm: AccountRealm, account: Account) {
    with(accountRealm) {
      if (accountId != account.accountId) {
        accountId = account.accountId
      }
      name = account.name
      secret = account.secret
      issuer = account.issuer.identifier
      order = account.order
      type = transformAccountType(account.type)
    }
  }

  private fun transformAccountType(otpType: String): OTPType {
    if (otpType == AccountRealm.HOTP_TYPE) {
      return OTPType.HOTP
    }

    return OTPType.TOTP
  }

  private fun transformAccountType(otpType: OTPType): String {
    if (otpType === OTPType.HOTP) {
      return AccountRealm.HOTP_TYPE
    }

    return AccountRealm.TOTP_TYPE
  }

  private fun transformIssuer(issuer: String): Issuer {
    return Issuer.fromString(issuer)
  }
}
