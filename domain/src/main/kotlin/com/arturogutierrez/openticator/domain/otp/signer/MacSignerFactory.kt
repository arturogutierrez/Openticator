package com.arturogutierrez.openticator.domain.otp.signer

import com.arturogutierrez.openticator.helper.Base32String
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

class MacSignerFactory {

  fun createMacSigner(secret: String): MacSigner {
    try {
      val secretBase32 = Base32String.decode(secret)
      val mac = Mac.getInstance("HMACSHA1")
      mac.init(SecretKeySpec(secretBase32, ""))

      return MacSigner(mac)
    } catch (e: Exception) {
      throw IllegalArgumentException(e.message)
    }
  }
}