package com.arturogutierrez.openticator.executor;

import javax.inject.Inject;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;

public class MainThread implements PostExecutionThread {

  @Inject
  public MainThread() {
    // Empty
  }

  @Override
  public Scheduler getScheduler() {
    return AndroidSchedulers.mainThread();
  }
}
