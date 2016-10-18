package com.arturogutierrez.openticator.interactor

import com.arturogutierrez.openticator.executor.PostExecutionThread
import com.arturogutierrez.openticator.executor.ThreadExecutor
import rx.Observable
import rx.Observer
import rx.Subscription
import rx.schedulers.Schedulers

abstract class Interactor<T>(val threadExecutor: ThreadExecutor, val postExecutionThread: PostExecutionThread) {

    private var subscription: Subscription? = null

    abstract fun createObservable(): Observable<T>

    fun execute(interactorObserver: Observer<T>) {
        subscription = createObservable().subscribeOn(Schedulers.from(threadExecutor)).observeOn(postExecutionThread.scheduler).subscribe(interactorObserver)
    }

    fun unsubscribe() {
        if (subscription != null && !subscription!!.isUnsubscribed) {
            subscription!!.unsubscribe()
        }
    }
}
