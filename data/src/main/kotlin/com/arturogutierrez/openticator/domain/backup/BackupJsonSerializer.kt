package com.arturogutierrez.openticator.domain.backup

import com.arturogutierrez.openticator.domain.account.model.Account
import com.arturogutierrez.openticator.domain.category.model.Category
import com.arturogutierrez.openticator.storage.json.mapper.AccountEntityMapper
import com.arturogutierrez.openticator.storage.json.mapper.CategoryEntityMapper
import com.arturogutierrez.openticator.storage.json.model.BackupEntity
import com.google.gson.Gson

class BackupJsonSerializer constructor(val categoryEntityMapper: CategoryEntityMapper,
                                       val accountEntityMapper: AccountEntityMapper,
                                       val gson: Gson) : BackupSerializer {

  override fun serialize(version: Int, categories: List<Category>, accounts: List<Pair<Account, Category>>): String {
    val categoryEntities = categoryEntityMapper.transform(categories)
    val accountEntities = accountEntityMapper.transform(accounts)
    val backupEntity = BackupEntity(version, categoryEntities, accountEntities)
    return gson.toJson(backupEntity)
  }

  override fun deserialize(json: String): List<Pair<Account, Category>> {
    throw UnsupportedOperationException("not implemented")
  }
}
