package com.arturogutierrez.openticator.storage

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

      val defaultRealm = Realm.getDefaultInstance()
      defaultRealm.executeTransaction { realm -> realm.copyToRealm(categoryRealm) }
      defaultRealm.close()

      category
    }

    return categoryObservable.doOnNext { categoryAdded -> notifyAccountChanges() }
  }

  override fun addAccount(category: Category, account: Account): Observable<Category> {
    return Observable.fromCallable {
      val defaultRealm = Realm.getDefaultInstance()
      defaultRealm.executeTransaction { realm ->
        val categoryRealm = getCategoryAsBlocking(realm, category.categoryId)
        val accountRealm = getAccountAsBlocking(realm, account.accountId)
        if (categoryRealm == null || accountRealm == null) {
          return@executeTransaction
        }

        accountRealm.category = categoryRealm
      }
      defaultRealm.close()

      category
    }
  }

  override val categories: Observable<List<Category>>
    get() = changesPublishSubject.map { categoriesAsBlocking }
        .startWith(Observable.just(categoriesAsBlocking))

  private val categoriesAsBlocking: List<Category>
    get() {
      val realm = Realm.getDefaultInstance()
      val realmResults = realm.where(CategoryRealm::class.java).findAllSorted("name")
      val categories = categoryRealmMapper.reverseTransform(realmResults)
      realm.close()

      return categories
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
