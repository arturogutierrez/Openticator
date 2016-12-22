package com.arturogutierrez.openticator.storage.json.mapper

import com.arturogutierrez.openticator.domain.account.model.Account
import com.arturogutierrez.openticator.domain.category.model.Category
import com.arturogutierrez.openticator.mapper.Mapper
import com.arturogutierrez.openticator.storage.json.model.AccountEntity
import javax.inject.Inject

class AccountEntityMapper @Inject constructor(val otpTypeMapper: OTPTypeMapper) : Mapper<Pair<Account, Category>, AccountEntity> {

  override fun transform(value: Pair<Account, Category>): AccountEntity {
    val account = value.first
    val category = value.second

    val issuerId = account.issuer.identifier
    val type = otpTypeMapper.transform(account.type)
    return AccountEntity(account.accountId, account.name, type, account.secret, issuerId,
        account.order, category.categoryId)
  }

  override fun reverseTransform(value: AccountEntity): Pair<Account, Category> {
    throw UnsupportedOperationException("not implemented")
  }
}
