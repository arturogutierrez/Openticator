package com.arturogutierrez.openticator.domain.password;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class PasswordSerializer {

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
    return Base64.getEncoder().encodeToString(digest);
  }

  private MessageDigest createMessageDigest() {
    try {
      return MessageDigest.getInstance("SHA-256");
    } catch (NoSuchAlgorithmException e) {
      // Should not happen, all Android devices should support SHA-256
      return null;
    }
  }
}
