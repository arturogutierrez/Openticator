package com.arturogutierrez.openticator.interactor

import rx.Observer

abstract class DefaultSubscriber<U> : Observer<U> {

  override fun onNext(item: U) {
    // Do nothing by default
  }

  override fun onCompleted() {
    // Do nothing by default
  }

  override fun onError(e: Throwable) {
    // TODO: Log to crashlytics
    // Do nothing by default
  }
}
