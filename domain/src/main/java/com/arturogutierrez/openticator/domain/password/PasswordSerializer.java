package com.arturogutierrez.openticator.domain.password;

public interface PasswordSerializer {

  String encodePassword(String plainPassword);
}
