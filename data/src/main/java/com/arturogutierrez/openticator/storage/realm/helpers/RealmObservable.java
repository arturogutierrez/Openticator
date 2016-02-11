package com.arturogutierrez.openticator.storage.realm.helpers;

import android.content.Context;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import rx.Observable;
import rx.functions.Func1;

public final class RealmObservable {

  private RealmObservable() {

  }

  public static <T extends RealmObject> Observable<T> object(Context context,
      final Func1<Realm, T> function) {
    return Observable.create(new OnSubscribeRealm<T>(context) {
      @Override
      public T get(Realm realm) {
        return function.call(realm);
      }
    });
  }

  public static <T extends RealmObject> Observable<RealmResults<T>> results(Context context,
      final Func1<Realm, RealmResults<T>> function) {
    return Observable.create(new OnSubscribeRealmResults<T>(context) {
      @Override
      public RealmResults<T> get(Realm realm) {
        return function.call(realm);
      }
    });
  }
}
