package com.arturogutierrez.openticator.domain.backup;

import android.util.Base64;
import com.arturogutierrez.openticator.domain.backup.exceptions.EncryptionException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.inject.Inject;

public class JSONEncryptorImpl implements JSONEncryptor {

  private static final String DELIMITER = "|";

  private static final String PKCS12_DERIVATION_ALGORITHM = "PBEWITHSHA256AND256BITAES-CBC-BC";
  private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";

  private static final int ITERATION_COUNT = 5000;
  private static final int KEY_LENGTH = 256;
  private static final int SALT_LENGTH = KEY_LENGTH / 8;

  private final SecureRandom secureRandom;

  @Inject
  public JSONEncryptorImpl() {
    this.secureRandom = new SecureRandom();
  }

  @Override
  public String encryptJSON(String JSON, String password) throws EncryptionException {
    try {
      Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
      byte[] salt = generateRandomSalt();
      byte[] iv = generateRandomIV(cipher.getBlockSize());
      SecretKey secretKey = deriveKeyPKCS12(password, salt);

      IvParameterSpec ivParams = new IvParameterSpec(iv);
      cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParams);
      byte[] cipherBytes = cipher.doFinal(JSON.getBytes());

      String saltBase64 = Base64.encodeToString(salt, Base64.NO_WRAP);
      String ivBase64 = Base64.encodeToString(iv, Base64.NO_WRAP);
      String cipherTextBase64 = Base64.encodeToString(cipherBytes, Base64.NO_WRAP);

      return String.format("%s%s%s%s%s", saltBase64, DELIMITER, ivBase64, DELIMITER,
          cipherTextBase64);
    } catch (Exception e) {
      throw new EncryptionException("Unable to encrypt JSON string");
    }
  }

  @Override
  public String decryptJSON(String cipherTextBase64, String password) throws EncryptionException {
    try {
      String[] fields = cipherTextBase64.split(DELIMITER);
      if (fields.length != 3) {
        throw new EncryptionException("Invalid encrypted text format");
      }

      byte[] salt = Base64.decode(fields[0], Base64.NO_WRAP);
      byte[] cipherBytes = Base64.decode(fields[1], Base64.NO_WRAP);
      SecretKey secretKey = deriveKeyPKCS12(password, salt);

      return decryptPKCS12(cipherBytes, secretKey, salt);
    } catch (Exception e) {
      throw new EncryptionException("Unable to decrypt the Base64 String");
    }
  }

  private byte[] generateRandomIV(int blockSize) {
    byte[] iv = new byte[blockSize];
    secureRandom.nextBytes(iv);
    return iv;
  }

  private byte[] generateRandomSalt() {
    byte[] salt = new byte[SALT_LENGTH];
    secureRandom.nextBytes(salt);
    return salt;
  }

  private SecretKey deriveKeyPKCS12(String password, byte[] salt) throws Exception {
    KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, ITERATION_COUNT, KEY_LENGTH);
    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(PKCS12_DERIVATION_ALGORITHM);
    return keyFactory.generateSecret(keySpec);
  }

  private String decryptPKCS12(byte[] cipherBytes, SecretKey secretKey, byte[] salt)
      throws Exception {
    Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
    PBEParameterSpec pbeSpec = new PBEParameterSpec(salt, ITERATION_COUNT);
    cipher.init(Cipher.DECRYPT_MODE, secretKey, pbeSpec);
    byte[] plainBytes = cipher.doFinal(cipherBytes);
    return new String(plainBytes, "UTF-8");
  }
}
