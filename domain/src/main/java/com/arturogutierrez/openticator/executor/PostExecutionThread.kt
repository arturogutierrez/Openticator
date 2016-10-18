package com.arturogutierrez.openticator.executor

import rx.Scheduler

/**
 * Executor where the response of the work done by interactors is delivered, it will be done on
 * UI thread to allow modify the UI
 */
interface PostExecutionThread {
    val scheduler: Scheduler
}
