package com.arturogutierrez.openticator.interactor;

import rx.Observer;

public abstract class DefaultSubscriber<T> implements Observer<T> {

  @Override
  public void onNext(T t) {
    // Do nothing by default
  }

  @Override
  public void onCompleted() {
    // Do nothing by default
  }

  @Override
  public void onError(Throwable e) {
    // Do nothing by default
  }
}
