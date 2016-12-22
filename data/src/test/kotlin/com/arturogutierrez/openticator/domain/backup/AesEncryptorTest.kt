package com.arturogutierrez.openticator.domain.backup

import com.arturogutierrez.openticator.RobolectricTestCase
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Ignore
import org.junit.Test

/*
 * I couldn't make encryption in Robolectic, even using BouncyCastle
 */
@Ignore
class AesEncryptorTest : RobolectricTestCase() {

  private lateinit var aesEncryptor: AesEncryptor

  @Before
  fun setUp() {
    aesEncryptor = AesEncryptor()
  }

  @Test
  fun shouldEncryptAndDecryptCorrectly() {
    val password = "pass"
    val input = "HOLA"

    val encodedBase64 = aesEncryptor.encrypt(input, password)
    val decodedText = aesEncryptor.decrypt(encodedBase64, password)

    assertThat(input, `is`(decodedText))
  }
}