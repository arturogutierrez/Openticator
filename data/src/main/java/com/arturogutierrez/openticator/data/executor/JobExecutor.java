package com.arturogutierrez.openticator.data.executor;

import android.support.annotation.NonNull;
import com.arturogutierrez.openticator.domain.executor.ThreadExecutor;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class JobExecutor implements ThreadExecutor {

  public static final int INITIAL_POOL_SIZE = 5;
  public static final int MAXIMUM_POOL_SIZE = 10;
  public static final int KEEP_ALIVE_TIME = 10;
  private final ThreadPoolExecutor threadPoolExecutor;
  private final BlockingQueue<Runnable> workQueue;
  private final ThreadFactory threadFactory;

  @Inject
  public JobExecutor() {
    this.workQueue = new LinkedBlockingQueue<>();
    this.threadFactory = new JobThreadFactory();
    this.threadPoolExecutor =
        new ThreadPoolExecutor(INITIAL_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME,
            TimeUnit.SECONDS, workQueue, threadFactory);
  }

  @Override
  public void execute(@NonNull Runnable runnable) {
    threadPoolExecutor.execute(runnable);
  }

  private static class JobThreadFactory implements ThreadFactory {
    private static final String THREAD_NAME = "android_";
    private int counter = 0;

    @Override
    public Thread newThread(@NonNull Runnable runnable) {
      return new Thread(runnable, THREAD_NAME + (counter++));
    }
  }
}
