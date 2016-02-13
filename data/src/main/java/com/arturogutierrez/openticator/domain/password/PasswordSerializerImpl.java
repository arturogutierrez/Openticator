package com.arturogutierrez.openticator.domain.password;

import android.util.Base64;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.inject.Inject;

public class PasswordSerializerImpl implements PasswordSerializer {

  @Inject
  public PasswordSerializerImpl() {

  }

  public String encodePassword(String plainPassword) {
    if (plainPassword == null) {
      return null;
    }

    MessageDigest messageDigest = createMessageDigest();
    if (messageDigest == null) {
      return null;
    }

    messageDigest.update(plainPassword.getBytes());
    byte[] digest = messageDigest.digest();
    return Base64.encodeToString(digest, Base64.NO_WRAP).trim();
  }

  private MessageDigest createMessageDigest() {
    try {
      return MessageDigest.getInstance("SHA-512");
    } catch (NoSuchAlgorithmException e) {
      // Should not happen, all Android devices should support SHA-256
      return null;
    }
  }
}
