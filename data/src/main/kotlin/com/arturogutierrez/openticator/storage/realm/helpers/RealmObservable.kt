package com.arturogutierrez.openticator.storage.realm.helpers

import io.realm.Realm
import io.realm.RealmObject
import io.realm.RealmResults
import rx.Observable
import rx.functions.Func1

object RealmObservable {

    fun <T : RealmObject> `object`(function: Func1<Realm, T>): Observable<T> {
        return Observable.create(object : OnSubscribeRealm<T>() {
            override fun get(realm: Realm): T {
                return function.call(realm)
            }
        })
    }

    fun <T : RealmObject> results(
            function: Func1<Realm, RealmResults<T>>): Observable<RealmResults<T>> {
        return Observable.create(object : OnSubscribeRealmResults<T>() {
            override fun get(realm: Realm): RealmResults<T> {
                return function.call(realm)
            }
        })
    }
}
