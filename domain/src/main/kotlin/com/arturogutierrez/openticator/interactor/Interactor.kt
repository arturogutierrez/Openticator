package com.arturogutierrez.openticator.interactor

import com.arturogutierrez.openticator.executor.PostExecutionThread
import com.arturogutierrez.openticator.executor.ThreadExecutor
import rx.Observable
import rx.Observer
import rx.Subscription
import rx.schedulers.Schedulers
import rx.subscriptions.Subscriptions

abstract class Interactor<in IN, OUT>(private val threadExecutor: ThreadExecutor,
                                      private val postExecutionThread: PostExecutionThread) {

  private var subscription: Subscription = Subscriptions.empty()

  abstract fun createObservable(params: IN): Observable<OUT>

  fun execute(params: IN, interactorObserver: Observer<OUT>) {
    subscription = createObservable(params)
        .subscribeOn(Schedulers.from(threadExecutor))
        .observeOn(postExecutionThread.scheduler)
        .subscribe(interactorObserver)
  }

  fun unsubscribe() {
    if (!subscription.isUnsubscribed) {
      subscription.unsubscribe()
    }
  }
}
