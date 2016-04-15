package com.arturogutierrez.openticator.domain.backup;

import com.arturogutierrez.openticator.domain.backup.exceptions.EncryptionException;

public interface JSONEncryptor {

  String encryptJSON(String JSON, String password) throws EncryptionException;

  String decryptJSON(String cipherTextBase64, String password) throws EncryptionException;

}
