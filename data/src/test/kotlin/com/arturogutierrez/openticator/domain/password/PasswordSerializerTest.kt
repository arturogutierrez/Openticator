package com.arturogutierrez.openticator.domain.password

import android.util.Base64
import com.arturogutierrez.openticator.RobolectricTestCase
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

class PasswordSerializerTest : RobolectricTestCase() {

  private lateinit var passwordSerializer: PasswordSerializer

  @Before
  fun setUp() {
    passwordSerializer = PasswordSerializerImpl()
  }

  @Test
  fun testEmptyPasswordReturnSerialization() {
    val encodedPassword = passwordSerializer.encodePassword("")

    assertThat<String>(encodedPassword,
        `is`("z4PhNX7vuL3xVChQ1m2AB9Yg5AULVxXcg/SpIdNs6c5H0NE8XYXysP+DGNKHfuwvY7kxvUdBeoGlODJ6+SfaPg=="))
  }

  @Test
  fun testSerializationWithSHA512Returns64Bytes() {
    val encodedPasswordInBase64 = passwordSerializer.encodePassword("password")
    val decodedPassword = Base64.decode(encodedPasswordInBase64, Base64.DEFAULT)

    assertThat(decodedPassword.size, `is`(64))
  }
}
