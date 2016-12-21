package com.arturogutierrez.openticator.domain.backup

import com.arturogutierrez.openticator.domain.account.model.Account
import rx.Observable

interface AccountSerializer {

  fun serialize(accounts: List<Account>): Observable<String>

  fun deserialize(json: String): Observable<List<Account>>
}
