package com.arturogutierrez.openticator.domain.password

interface PasswordSerializer {
  fun encodePassword(plainPassword: String): String?
}
