package com.arturogutierrez.openticator.domain.account.interactor;

import com.arturogutierrez.openticator.domain.backup.BackupManager;
import com.arturogutierrez.openticator.executor.PostExecutionThread;
import com.arturogutierrez.openticator.executor.ThreadExecutor;
import com.arturogutierrez.openticator.interactor.Interactor;
import rx.Observable;

public class ImportExternalBackupInteractor extends Interactor<Integer> {

  private final BackupManager backupManager;
  private String password;

  public ImportExternalBackupInteractor(BackupManager backupManager, ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.backupManager = backupManager;
  }

  public void configure(String password) {
    this.password = password;
  }

  @Override
  public Observable<Integer> createObservable() {
    if (password == null) {
      throw new IllegalStateException("You must call configure before execute the interactor");
    }

    return backupManager.restoreBackup(password);
  }
}
