package com.arturogutierrez.openticator.interactor

import com.arturogutierrez.openticator.executor.PostExecutionThread
import com.arturogutierrez.openticator.executor.ThreadExecutor
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber

abstract class FlowableUseCase<OUT, in Params>(
    private val threadExecutor: ThreadExecutor,
    private val postExecutionThread: PostExecutionThread) {

  private var disposables: CompositeDisposable = CompositeDisposable()

  abstract fun buildObservable(params: Params): Flowable<OUT>

  fun execute(params: Params, subscriber: DisposableSubscriber<OUT>) {
    val disposable = buildObservable(params)
        .subscribeOn(Schedulers.from(threadExecutor))
        .observeOn(postExecutionThread.scheduler)
        .subscribeWith(subscriber)
    disposables.add(disposable)
  }

  fun dispose() {
    if (!disposables.isDisposed) {
      disposables.dispose()
    }
  }
}
