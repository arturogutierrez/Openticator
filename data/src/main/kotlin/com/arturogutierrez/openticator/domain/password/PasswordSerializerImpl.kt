package com.arturogutierrez.openticator.domain.password

import android.util.Base64
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import javax.inject.Inject

class PasswordSerializerImpl @Inject constructor() : PasswordSerializer {

  override fun encodePassword(plainPassword: String): String? {
    val messageDigest = createMessageDigest() ?: return null

    messageDigest.update(plainPassword.toByteArray())
    val digest = messageDigest.digest()
    return Base64.encodeToString(digest, Base64.NO_WRAP).trim { it <= ' ' }
  }

  private fun createMessageDigest(): MessageDigest? {
    return try {
      MessageDigest.getInstance("SHA-512")
    } catch (e: NoSuchAlgorithmException) {
      // Should not happen, all Android devices should support SHA-256
      null
    }
  }
}
