package com.arturogutierrez.openticator.storage.realm.helpers

import io.realm.Realm
import io.realm.RealmObject
import io.realm.exceptions.RealmException
import rx.Observable
import rx.Subscriber

abstract class OnSubscribeRealm<T : RealmObject> : Observable.OnSubscribe<T> {

  override fun call(subscriber: Subscriber<in T>) {
    Realm.getDefaultInstance().use {
      val value: T
      it.beginTransaction()
      try {
        value = get(it)
        it.commitTransaction()
      } catch (e: RuntimeException) {
        it.cancelTransaction()
        subscriber.onError(RealmException("Error during transaction.", e))
        return
      } catch (e: Error) {
        it.cancelTransaction()
        subscriber.onError(e)
        return
      }

      subscriber.onNext(value)
      subscriber.onCompleted()
    }
  }

  abstract operator fun get(realm: Realm): T
}
