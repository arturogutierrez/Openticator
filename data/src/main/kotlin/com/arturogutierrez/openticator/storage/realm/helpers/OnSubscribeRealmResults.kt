package com.arturogutierrez.openticator.storage.realm.helpers

import io.realm.Realm
import io.realm.RealmObject
import io.realm.RealmResults
import io.realm.exceptions.RealmException
import rx.Observable
import rx.Subscriber

abstract class OnSubscribeRealmResults<T : RealmObject> : Observable.OnSubscribe<RealmResults<T>> {

  override fun call(subscriber: Subscriber<in RealmResults<T>>) {
    Realm.getDefaultInstance().use {
      val results: RealmResults<T>
      it.beginTransaction()
      try {
        results = get(it)
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

      subscriber.onNext(results)
      subscriber.onCompleted()
    }
  }

  abstract operator fun get(realm: Realm): RealmResults<T>
}
