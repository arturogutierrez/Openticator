package com.arturogutierrez.openticator.interactor

import io.reactivex.observers.DisposableMaybeObserver

open class DefaultMaybeObserver<T> : DisposableMaybeObserver<T>() {

  override fun onComplete() {}

  override fun onSuccess(t: T) {}

  override fun onError(e: Throwable) {
    println(e)
  }
}
