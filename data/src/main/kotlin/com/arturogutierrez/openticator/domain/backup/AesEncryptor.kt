package com.arturogutierrez.openticator.domain.backup

import android.util.Base64
import org.spongycastle.crypto.engines.AESEngine
import org.spongycastle.crypto.modes.CBCBlockCipher
import org.spongycastle.crypto.paddings.PaddedBufferedBlockCipher
import org.spongycastle.crypto.params.KeyParameter
import java.nio.charset.Charset
import java.security.SecureRandom
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec


class AesEncryptor : SimpleEncryptor {
  private companion object {

    val DELIMITER = "]"
    val PKCS12_DERIVATION_ALGORITHM = "PBEWITHSHA256AND256BITAES-CBC-BC"
    val ITERATION_COUNT = 1000
    val KEY_LENGTH = 256
    val SALT_LENGTH = 8
  }
  private val secureRandom = SecureRandom()

  override fun encrypt(input: String, password: String): String {
    val salt = generateRandomSalt()
    val secretKey = deriveKeyPKCS12(password, salt)

    val cipherBytes = encryptPKCS12(input, secretKey)

    val saltBase64 = Base64.encodeToString(salt, Base64.NO_WRAP)
    val cipherTextBase64 = Base64.encodeToString(cipherBytes, Base64.NO_WRAP)
    return String.format("%s%s%s", saltBase64, DELIMITER, cipherTextBase64)
  }

  override fun decrypt(cipherTextBase64: String, password: String): String {
    val fields = cipherTextBase64.split(DELIMITER)
    if (fields.size != 2) {
      throw IllegalArgumentException("Invalid encrypted text format")
    }

    val salt = Base64.decode(fields[0], Base64.NO_WRAP)
    val cipherBytes = Base64.decode(fields[1], Base64.NO_WRAP)

    val secretKey = deriveKeyPKCS12(password, salt)

    return decryptPKCS12(cipherBytes, secretKey)
  }

  private fun generateRandomSalt(): ByteArray {
    val salt = ByteArray(SALT_LENGTH)
    secureRandom.nextBytes(salt)
    return salt
  }

  private fun deriveKeyPKCS12(password: String, salt: ByteArray): SecretKey {
    val keySpec = PBEKeySpec(password.toCharArray(), salt, ITERATION_COUNT, KEY_LENGTH)
    val keyFactory = SecretKeyFactory.getInstance(PKCS12_DERIVATION_ALGORITHM, "BC")
    return keyFactory.generateSecret(keySpec)
  }

  private fun encryptPKCS12(JSON: String, secretKey: SecretKey): ByteArray {
    val cipherParameters = KeyParameter(secretKey.encoded)
    val cipher = PaddedBufferedBlockCipher(CBCBlockCipher(AESEngine()))
    cipher.init(true, cipherParameters)

    val plainBytes = JSON.toByteArray(charset("UTF-8"))
    val encodedBytes = ByteArray(cipher.getOutputSize(plainBytes.size))

    val processedBytes = cipher.processBytes(plainBytes, 0, plainBytes.size, encodedBytes, 0)
    cipher.doFinal(encodedBytes, processedBytes)

    return encodedBytes
  }

  private fun decryptPKCS12(cipherBytes: ByteArray, secretKey: SecretKey): String {
    val cipherParameters = KeyParameter(secretKey.encoded)
    val cipher = PaddedBufferedBlockCipher(CBCBlockCipher(AESEngine()))
    cipher.init(false, cipherParameters)

    val decodedBytes = ByteArray(cipher.getOutputSize(cipherBytes.size))

    var processedBytes = cipher.processBytes(cipherBytes, 0, cipherBytes.size, decodedBytes, 0)
    processedBytes += cipher.doFinal(decodedBytes, processedBytes)

    val JSON = decodedBytes.copyOfRange(0, processedBytes).toString(Charset.defaultCharset())
    return JSON
  }
}