package com.arturogutierrez.openticator.executor

import rx.Scheduler
import rx.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class MainThread @Inject constructor() : PostExecutionThread {
  override val scheduler: Scheduler
    get() = AndroidSchedulers.mainThread()
}
