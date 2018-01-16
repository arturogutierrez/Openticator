package com.arturogutierrez.openticator.interactor

import com.arturogutierrez.openticator.executor.PostExecutionThread
import com.arturogutierrez.openticator.executor.ThreadExecutor
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers

abstract class CompletableUseCase<in Params> constructor(
    private val threadExecutor: ThreadExecutor,
    private val postExecutionThread: PostExecutionThread) {

  private val subscription = Disposables.empty()

  protected abstract fun buildObservable(params: Params): Completable

  fun execute(observer: CompletableObserver, params: Params) {
    buildObservable(params)
        .subscribeOn(Schedulers.from(threadExecutor))
        .observeOn(postExecutionThread.scheduler)
        .subscribeWith(observer)
  }

  fun unsubscribe() {
    if (!subscription.isDisposed) {
      subscription.dispose()
    }
  }
}
