package com.arturogutierrez.openticator.interactor

import com.arturogutierrez.openticator.executor.PostExecutionThread
import com.arturogutierrez.openticator.executor.ThreadExecutor
import rx.Observable
import rx.Observer
import rx.Subscription
import rx.schedulers.Schedulers
import rx.subscriptions.Subscriptions

abstract class Interactor<T>(private val threadExecutor: ThreadExecutor,
                             private val postExecutionThread: PostExecutionThread) {

  private var subscription: Subscription = Subscriptions.empty()

  abstract fun createObservable(): Observable<T>

  fun execute(interactorObserver: Observer<T>) {
    subscription = createObservable()
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
