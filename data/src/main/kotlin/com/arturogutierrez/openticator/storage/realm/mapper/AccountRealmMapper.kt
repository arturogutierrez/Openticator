package com.arturogutierrez.openticator.storage.realm.mapper

import com.arturogutierrez.openticator.domain.account.model.Account
import com.arturogutierrez.openticator.domain.account.model.OTPType
import com.arturogutierrez.openticator.domain.issuer.model.Issuer
import com.arturogutierrez.openticator.mapper.Mapper
import com.arturogutierrez.openticator.storage.realm.model.AccountRealm
import javax.inject.Inject

class AccountRealmMapper @Inject constructor() : Mapper<Account, AccountRealm> {

  override fun transform(account: Account): AccountRealm {
    val accountRealm = AccountRealm()
    copyToAccountRealm(accountRealm, account)
    return accountRealm
  }

  override fun reverseTransform(accountRealm: AccountRealm): Account {
    val issuer = transformIssuer(accountRealm.issuer)
    val account = Account(accountRealm.accountId, accountRealm.name,
        transformAccountType(accountRealm.type), accountRealm.secret, issuer,
        accountRealm.order)
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
