package com.arturogutierrez.openticator.storage.realm

import com.arturogutierrez.openticator.domain.account.model.Account
import com.arturogutierrez.openticator.domain.category.model.Category
import com.arturogutierrez.openticator.domain.category.repository.CategoryDataStore
import com.arturogutierrez.openticator.storage.realm.mapper.CategoryRealmMapper
import com.arturogutierrez.openticator.storage.realm.model.AccountRealm
import com.arturogutierrez.openticator.storage.realm.model.CategoryRealm
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.processors.BehaviorProcessor
import io.reactivex.processors.PublishProcessor
import io.realm.Realm
import javax.inject.Inject

class CategoryDiskDataStore @Inject constructor(private val categoryRealmMapper: CategoryRealmMapper) : CategoryDataStore {

  private val changesPublishSubject = BehaviorProcessor.create<Unit>().toSerialized()

  override fun add(category: Category): Single<Category> {
    val categorySingle = Single.fromCallable {
      val categoryRealm = categoryRealmMapper.transform(category)

      Realm.getDefaultInstance().use {
        it.executeTransaction { it.copyToRealm(categoryRealm) }
      }

      category
    }

    return categorySingle.doOnSuccess { notifyAccountChanges() }
  }

  override fun addAccount(category: Category, account: Account): Single<Category> {
    return Single.fromCallable {
      Realm.getDefaultInstance().use {
        it.executeTransaction {
          val categoryRealm = getCategoryAsBlocking(it, category.categoryId)
          val accountRealm = getAccountAsBlocking(it, account.accountId)
          if (categoryRealm == null || accountRealm == null) {
            return@executeTransaction
          }

          accountRealm.category = categoryRealm
        }
      }

      category
    }
  }

  override val categories: Flowable<List<Category>>
    get() = changesPublishSubject.map { categoriesAsBlocking }
        .startWith(Flowable.just(categoriesAsBlocking))

  private val categoriesAsBlocking: List<Category>
    get() {
      Realm.getDefaultInstance().use {
        val realmResults = it.where(CategoryRealm::class.java).findAllSorted("name")
        return categoryRealmMapper.reverseTransform(realmResults)
      }
    }

  private fun getCategoryAsBlocking(realm: Realm, categoryId: String): CategoryRealm? {
    return realm.where(CategoryRealm::class.java).equalTo("categoryId", categoryId).findFirst()
  }

  private fun getAccountAsBlocking(realm: Realm, accountId: String): AccountRealm? {
    return realm.where(AccountRealm::class.java).equalTo("accountId", accountId).findFirst()
  }

  private fun notifyAccountChanges() {
    changesPublishSubject.onNext(Unit)
  }
}
