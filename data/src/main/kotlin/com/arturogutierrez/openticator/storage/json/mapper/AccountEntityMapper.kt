package com.arturogutierrez.openticator.storage.json.mapper

import com.arturogutierrez.openticator.domain.account.model.Account
import com.arturogutierrez.openticator.domain.category.model.Category
import com.arturogutierrez.openticator.mapper.Mapper
import com.arturogutierrez.openticator.storage.json.model.AccountEntity
import javax.inject.Inject

class AccountEntityMapper @Inject constructor(val otpTypeMapper: OTPTypeMapper) : Mapper<Pair<Account, Category>, AccountEntity> {

  override fun transform(pair: Pair<Account, Category>): AccountEntity {
    val account = pair.first
    val category = pair.second

    val issuerId = account.issuer.identifier
    val type = otpTypeMapper.transform(account.type)
    return AccountEntity(account.accountId, account.name, type, account.secret, issuerId,
        account.order, category.categoryId)
  }

  override fun reverseTransform(accountEntity: AccountEntity): Pair<Account, Category> {
    throw UnsupportedOperationException("not implemented")
  }
}
