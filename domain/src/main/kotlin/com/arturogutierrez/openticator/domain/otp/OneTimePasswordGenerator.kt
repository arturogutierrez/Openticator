package com.arturogutierrez.openticator.domain.otp

import com.arturogutierrez.openticator.domain.otp.signer.MacSigner
import com.arturogutierrez.openticator.domain.otp.signer.MacSignerFactory
import java.nio.ByteBuffer

internal class OneTimePasswordGenerator(secret: String, private val passwordLength: Int) {
  private val macSigner: MacSigner

  init {
    this.macSigner = createMacSigner(secret)
  }

  fun generate(state: Long): String {
    val stateInBytes = ByteBuffer.allocate(java.lang.Long.BYTES).putLong(state).array()
    return generate(stateInBytes)
  }

  private fun createMacSigner(secret: String): MacSigner {
    val macSignerFactory = MacSignerFactory()
    return macSignerFactory.createMacSigner(secret)
  }

  private fun generate(stateInBytes: ByteArray): String {
    val stateHash = macSigner.sign(stateInBytes)

    // offset is the low order bits of the last byte of the hash
    val offset = stateHash[stateHash.size - 1].toInt() and 0xF
    // Grab a positive integer value starting at the given offset.
    val truncatedHash = byteArrayToInt(stateHash, offset) and 0x7FFFFFFF
    val pinValue = truncatedHash % Math.pow(10.0, passwordLength.toDouble()).toInt()

    return String.format("%0" + passwordLength + "d", pinValue)
  }

  private fun byteArrayToInt(bytes: ByteArray, offset: Int): Int {
    var value = 0
    for (i in 0..Integer.BYTES - 1) {
      val shift = (Integer.BYTES - 1 - i) * 8
      value += bytes[i + offset].toInt() and 0xFF shl shift
    }
    return value
  }
}
