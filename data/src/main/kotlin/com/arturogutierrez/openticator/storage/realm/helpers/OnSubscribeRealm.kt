package com.arturogutierrez.openticator.storage.realm.helpers

import io.realm.Realm
import io.realm.RealmObject
import io.realm.exceptions.RealmException
import rx.Observable
import rx.Subscriber

abstract class OnSubscribeRealm<T : RealmObject> : Observable.OnSubscribe<T> {

  override fun call(subscriber: Subscriber<in T>) {
    val realm = Realm.getDefaultInstance()

    val value: T
    realm.beginTransaction()
    try {
      value = get(realm)
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

    subscriber.onNext(value)
    subscriber.onCompleted()

    realm.close()
  }

  abstract operator fun get(realm: Realm): T
}
