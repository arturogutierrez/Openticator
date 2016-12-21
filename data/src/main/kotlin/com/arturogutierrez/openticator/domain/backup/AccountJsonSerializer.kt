package com.arturogutierrez.openticator.domain.backup

import com.arturogutierrez.openticator.domain.account.model.Account
import com.arturogutierrez.openticator.domain.category.model.Category
import com.arturogutierrez.openticator.storage.json.mapper.AccountEntityMapper
import com.google.gson.Gson

class AccountJsonSerializer constructor(val accountEntityMapper: AccountEntityMapper,
                                        val gson: Gson) : AccountSerializer {

  override fun serialize(accounts: List<Pair<Account, Category>>): String {
    return gson.toJson(accounts.map { accountEntityMapper.transform(it) })
  }

  override fun deserialize(json: String): List<Pair<Account, Category>> {
    throw UnsupportedOperationException("not implemented")
  }
}
