package com.arturogutierrez.openticator.domain.account.interactor;

import com.arturogutierrez.openticator.domain.Preferences;
import com.arturogutierrez.openticator.domain.backup.BackupManager;
import com.arturogutierrez.openticator.executor.PostExecutionThread;
import com.arturogutierrez.openticator.executor.ThreadExecutor;
import com.arturogutierrez.openticator.interactor.Interactor;
import rx.Observable;

public class CreateExternalBackupInteractor extends Interactor<String> {

  private final BackupManager backupManager;
  private final Preferences preferences;

  public CreateExternalBackupInteractor(BackupManager backupManager, Preferences preferences,
      ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.backupManager = backupManager;
    this.preferences = preferences;
  }

  @Override
  public Observable<String> createObservable() {
    return backupManager.createBackup(preferences.getMasterPassword());
  }
}
