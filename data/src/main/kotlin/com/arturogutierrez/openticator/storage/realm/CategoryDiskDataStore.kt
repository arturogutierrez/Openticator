package com.arturogutierrez.openticator.storage.realm

import com.arturogutierrez.openticator.domain.account.model.Account
import com.arturogutierrez.openticator.domain.category.model.Category
import com.arturogutierrez.openticator.domain.category.repository.CategoryDataStore
import com.arturogutierrez.openticator.storage.realm.mapper.CategoryRealmMapper
import com.arturogutierrez.openticator.storage.realm.model.AccountRealm
import com.arturogutierrez.openticator.storage.realm.model.CategoryRealm
import io.realm.Realm
import rx.Observable
import rx.subjects.PublishSubject
import javax.inject.Inject

class CategoryDiskDataStore @Inject constructor(private val categoryRealmMapper: CategoryRealmMapper) : CategoryDataStore {

  private val changesPublishSubject = PublishSubject.create<Void>().toSerialized()

  override fun add(category: Category): Observable<Category> {
    val categoryObservable = Observable.fromCallable {
      val categoryRealm = categoryRealmMapper.transform(category)

      Realm.getDefaultInstance().use {
        it.executeTransaction { it.copyToRealm(categoryRealm) }
      }

      category
    }

    return categoryObservable.doOnNext { notifyAccountChanges() }
  }

  override fun addAccount(category: Category, account: Account): Observable<Category> {
    return Observable.fromCallable {
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

  override val categories: Observable<List<Category>>
    get() = changesPublishSubject.map { categoriesAsBlocking }
        .startWith(Observable.just(categoriesAsBlocking))

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
    changesPublishSubject.onNext(null)
  }
}
