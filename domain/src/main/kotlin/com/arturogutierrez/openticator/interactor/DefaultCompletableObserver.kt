package com.arturogutierrez.openticator.interactor

import io.reactivex.CompletableObserver
import io.reactivex.disposables.Disposable

open class DefaultCompletableObserver : CompletableObserver {

  override fun onComplete() {}

  override fun onSubscribe(disposable: Disposable) {}

  override fun onError(e: Throwable) {}
}
