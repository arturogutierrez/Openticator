package com.arturogutierrez.openticator.domain.interactor;

import com.arturogutierrez.openticator.domain.executor.PostExecutionThread;
import com.arturogutierrez.openticator.domain.executor.ThreadExecutor;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.schedulers.Schedulers;

public abstract class Interactor<T> {

  private final ThreadExecutor threadExecutor;
  private final PostExecutionThread postExecutionThread;

  private Subscription subscription;

  public Interactor(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
    this.threadExecutor = threadExecutor;
    this.postExecutionThread = postExecutionThread;
  }

  public abstract Observable<T> createObservable();

  public void execute(Observer<T> interactorObserver) {
    subscription = createObservable().subscribeOn(Schedulers.from(threadExecutor))
        .observeOn(postExecutionThread.getScheduler())
        .subscribe(interactorObserver);
  }

  public void unsubscribe() {
    if (subscription != null && !subscription.isUnsubscribed()) {
      subscription.unsubscribe();
    }
  }
}
