package com.arturogutierrez.openticator.executor

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class MainThread @Inject constructor() : PostExecutionThread {
  override val scheduler: Scheduler
    get() = AndroidSchedulers.mainThread()
}
