package com.arturogutierrez.openticator.domain.backup

import com.arturogutierrez.openticator.domain.account.model.Account
import com.arturogutierrez.openticator.domain.category.model.Category

interface AccountSerializer {

  fun serialize(accounts: List<Pair<Account, Category>>): String

  fun deserialize(json: String): List<Pair<Account, Category>>
}
