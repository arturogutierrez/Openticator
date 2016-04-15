package com.arturogutierrez.openticator.domain.backup;

import com.arturogutierrez.openticator.domain.account.repository.AccountRepository;
import com.arturogutierrez.openticator.domain.backup.exceptions.EncryptionException;
import javax.inject.Inject;
import rx.Observable;

public class BackupManager {

  private final AccountRepository accountRepository;
  private final AccountsSerializator accountsSerializator;
  private final JSONEncryptor jsonEncryptor;

  @Inject
  public BackupManager(AccountRepository accountRepository,
      AccountsSerializator accountsSerializator, JSONEncryptor jsonEncryptor) {
    this.accountRepository = accountRepository;
    this.accountsSerializator = accountsSerializator;
    this.jsonEncryptor = jsonEncryptor;
  }

  public Observable<String> createBackup(String password) {
    return accountRepository.getAllAccounts()
        .flatMap(accountsSerializator::serializeAccounts)
        .flatMap(s -> {
          try {
            return Observable.just(jsonEncryptor.encryptJSON(s, password));
          } catch (EncryptionException e) {
            return Observable.error(e);
          }
        }).flatMap(encrypted -> {
          // TODO: Save in SD
          return Observable.just("fileName");
        });
  }
}
