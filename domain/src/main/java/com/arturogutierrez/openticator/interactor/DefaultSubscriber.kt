package com.arturogutierrez.openticator.interactor

import rx.Observer

abstract class DefaultSubscriber<T> : Observer<T> {

    override fun onNext(t: T) {
        // Do nothing by default
    }

    override fun onCompleted() {
        // Do nothing by default
    }

    override fun onError(e: Throwable) {
        // Do nothing by default
    }
}
