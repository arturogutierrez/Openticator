package com.arturogutierrez.openticator.storage.realm.helpers;

import android.content.Context;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.exceptions.RealmException;
import rx.Observable;
import rx.Subscriber;

public abstract class OnSubscribeRealm<T extends RealmObject> implements Observable.OnSubscribe<T> {

  private final Context context;

  public OnSubscribeRealm(Context context) {
    this.context = context;
  }

  @Override
  public void call(Subscriber<? super T> subscriber) {
    final Realm realm = Realm.getInstance(context);

    T object;
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

  public abstract T get(Realm realm);
}
