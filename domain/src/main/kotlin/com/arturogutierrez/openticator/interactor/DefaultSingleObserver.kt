package com.arturogutierrez.openticator.interactor

import io.reactivex.observers.DisposableSingleObserver

open class DefaultSingleObserver<T> : DisposableSingleObserver<T>() {

  override fun onSuccess(t: T) {}

  override fun onError(e: Throwable) {}
}
