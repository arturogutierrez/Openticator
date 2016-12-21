package com.arturogutierrez.openticator.storage.realm

import com.arturogutierrez.openticator.domain.account.model.Account
import com.arturogutierrez.openticator.domain.account.repository.AccountCategoryDataStore
import com.arturogutierrez.openticator.domain.category.model.Category
import com.arturogutierrez.openticator.storage.realm.mapper.AccountRealmMapper
import com.arturogutierrez.openticator.storage.realm.mapper.CategoryRealmMapper
import com.arturogutierrez.openticator.storage.realm.model.AccountRealm
import io.realm.Realm
import rx.Observable
import javax.inject.Inject

class AccountCategoryDiskDataStore @Inject constructor(
    private val accountRealmMapper: AccountRealmMapper,
    private val categoryRealmMapper: CategoryRealmMapper) : AccountCategoryDataStore {

  override val allAccounts: Observable<List<Pair<Account, Category>>>
    get() = Observable.fromCallable {
      Realm.getDefaultInstance().use {
        val results = it.where(AccountRealm::class.java).findAll()
        results.map {
          Pair(accountRealmMapper.reverseTransform(it), getCategoryForAccountRealm(it))
        }
      }
    }

  private fun getCategoryForAccountRealm(accountRealm: AccountRealm): Category {
    val categoryRealm = accountRealm.category
    if (categoryRealm != null) {
      return categoryRealmMapper.reverseTransform(categoryRealm)
    }

    return Category.empty
  }
}
