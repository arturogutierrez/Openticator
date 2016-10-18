package com.arturogutierrez.openticator.domain.otp

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class OneTimePasswordGeneratorTest {

    @Test
    fun testGenerateCodeForFirstState() {
        val secret = "ABCDEFGHIJK23456"
        val oneTimePasswordGenerator = OneTimePasswordGenerator(secret, 6)

        val code = oneTimePasswordGenerator.generate(0)

        assertThat(code, equalTo("867671"))
    }

    @Test
    fun testGenerateCodeForNinthState() {
        val secret = "ABCDEFGHIJK23456"
        val oneTimePasswordGenerator = OneTimePasswordGenerator(secret, 6)

        val code = oneTimePasswordGenerator.generate(9)

        assertThat(code, equalTo("877436"))
    }
}
