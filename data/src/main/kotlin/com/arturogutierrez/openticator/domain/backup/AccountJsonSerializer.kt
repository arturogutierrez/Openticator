package com.arturogutierrez.openticator.domain.backup

import com.arturogutierrez.openticator.domain.account.model.Account
import com.arturogutierrez.openticator.storage.json.mapper.AccountEntityMapper
import com.google.gson.Gson
import rx.Observable

class AccountJsonSerializer constructor(val accountEntityMapper: AccountEntityMapper,
                                        val gson: Gson) : AccountSerializer {

  override fun serialize(accounts: List<Account>): Observable<String> {
    return Observable.fromCallable {
      gson.toJson(accounts.map { accountEntityMapper.transform(it) })
    }
  }

  override fun deserialize(json: String): Observable<List<Account>> {
    throw UnsupportedOperationException("not implemented")
  }
}
