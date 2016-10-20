package com.arturogutierrez.openticator.storage.realm.helpers

import io.realm.Realm
import io.realm.RealmObject
import io.realm.RealmResults
import io.realm.exceptions.RealmException
import rx.Observable
import rx.Subscriber

abstract class OnSubscribeRealmResults<T : RealmObject> : Observable.OnSubscribe<RealmResults<T>> {

  override fun call(subscriber: Subscriber<in RealmResults<T>>) {
    val realm = Realm.getDefaultInstance()

    val results: RealmResults<T>
    realm.beginTransaction()
    try {
      results = get(realm)
      realm.commitTransaction()
    } catch (e: RuntimeException) {
      realm.cancelTransaction()
      subscriber.onError(RealmException("Error during transaction.", e))
      return
    } catch (e: Error) {
      realm.cancelTransaction()
      subscriber.onError(e)
      return
    }

    subscriber.onNext(results)
    subscriber.onCompleted()

    realm.close()
  }

  abstract operator fun get(realm: Realm): RealmResults<T>
}
