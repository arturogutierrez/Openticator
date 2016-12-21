package com.arturogutierrez.openticator.storage.json.mapper

import com.arturogutierrez.openticator.domain.account.model.Account
import com.arturogutierrez.openticator.mapper.Mapper
import com.arturogutierrez.openticator.storage.json.model.AccountEntity
import javax.inject.Inject

class AccountEntityMapper @Inject constructor(val otpTypeMapper: OTPTypeMapper) : Mapper<Account, AccountEntity> {

  override fun transform(account: Account): AccountEntity {
    val issuerId = account.issuer.identifier
    val type = otpTypeMapper.transform(account.type)
    return AccountEntity(account.accountId, account.name, type, account.secret, issuerId,
        account.order)
  }

  override fun reverseTransform(otpType: AccountEntity): Account {
    throw UnsupportedOperationException("not implemented")
  }
}
