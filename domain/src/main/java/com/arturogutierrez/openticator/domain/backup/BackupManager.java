package com.arturogutierrez.openticator.domain.backup;

import com.arturogutierrez.openticator.domain.account.repository.AccountRepository;
import com.arturogutierrez.openticator.domain.backup.exceptions.EncryptionException;
import com.arturogutierrez.openticator.domain.backup.exceptions.ExternalBackupException;
import com.arturogutierrez.openticator.storage.ExternalStorage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import javax.inject.Inject;
import rx.Observable;

public class BackupManager {

  private static final String BACKUP_FILENAME = "Openticator.db";

  private final AccountRepository accountRepository;
  private final AccountsSerializator accountsSerializator;
  private final JSONEncryptor jsonEncryptor;
  private final ExternalStorage externalStorage;

  @Inject
  public BackupManager(AccountRepository accountRepository,
      AccountsSerializator accountsSerializator, JSONEncryptor jsonEncryptor,
      ExternalStorage externalStorage) {
    this.accountRepository = accountRepository;
    this.accountsSerializator = accountsSerializator;
    this.jsonEncryptor = jsonEncryptor;
    this.externalStorage = externalStorage;
  }

  /**
   * Create encrypted backup in a default external directory.
   * The observable can throw:
   * <br/>- EncryptionException if an error occurs while encryption
   * <br/>- ExternalBackupException if the app couldn't write in the external storage
   *
   * @param password the master hashed password
   * @return the absolute path of the fresh backup created
   */
  public Observable<String> createBackup(String password) {
    return accountRepository.getAllAccounts()
        .flatMap(accountsSerializator::serializeAccounts)
        .flatMap(s -> {
          try {
            return Observable.just(jsonEncryptor.encryptJSON(s, password));
          } catch (EncryptionException e) {
            return Observable.error(e);
          }
        })
        .flatMap(encrypted -> Observable.fromCallable(() -> {
          try {
            File backupFile = externalStorage.getExternalStoragePublicFile(BACKUP_FILENAME);
            writeBackupToFile(encrypted, backupFile);
            return backupFile.getAbsolutePath();
          } catch (IOException e) {
            throw new ExternalBackupException("Unable to create filename in external storage");
          }
        }));
  }

  private void writeBackupToFile(String backupInfo, File backupFile)
      throws ExternalBackupException {
    try {
      FileOutputStream fileOutputStream = new FileOutputStream(backupFile);
      OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
      outputStreamWriter.write(backupInfo);
      outputStreamWriter.close();
    } catch (IOException e) {
      throw new ExternalBackupException("Unable to create backup in external storage");
    }
  }
}
