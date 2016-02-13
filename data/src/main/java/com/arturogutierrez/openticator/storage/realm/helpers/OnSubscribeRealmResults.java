package com.arturogutierrez.openticator.storage.realm.helpers;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.exceptions.RealmException;
import rx.Observable;
import rx.Subscriber;

public abstract class OnSubscribeRealmResults<T extends RealmObject>
    implements Observable.OnSubscribe<RealmResults<T>> {

  @Override
  public void call(Subscriber<? super RealmResults<T>> subscriber) {
    final Realm realm = Realm.getDefaultInstance();

    RealmResults<T> object;
    realm.beginTransaction();
    try {
      object = get(realm);
      realm.commitTransaction();
    } catch (RuntimeException e) {
      realm.cancelTransaction();
      subscriber.onError(new RealmException("Error during transaction.", e));
      return;
    } catch (Error e) {
      realm.cancelTransaction();
      subscriber.onError(e);
      return;
    }

    subscriber.onNext(object);
    subscriber.onCompleted();

    realm.close();
  }

  public abstract RealmResults<T> get(Realm realm);
}
