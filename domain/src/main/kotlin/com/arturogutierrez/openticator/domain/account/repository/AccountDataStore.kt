package com.arturogutierrez.openticator.domain.account.repository

import com.arturogutierrez.openticator.domain.account.model.Account
import com.arturogutierrez.openticator.domain.category.model.Category
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface AccountDataStore {

  fun add(account: Account): Single<Account>

  fun update(account: Account): Single<Account>

  fun remove(account: Account): Completable

  fun getAccounts(category: Category): Flowable<List<Account>>

  val allAccounts: Flowable<List<Account>>
}
