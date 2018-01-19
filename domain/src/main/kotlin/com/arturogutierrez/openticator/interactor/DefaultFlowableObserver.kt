package com.arturogutierrez.openticator.interactor

import io.reactivex.subscribers.DisposableSubscriber

open class DefaultFlowableObserver<T> : DisposableSubscriber<T>() {

  override fun onNext(t: T) {}

  override fun onComplete() {}

  override fun onError(e: Throwable) {}
}
