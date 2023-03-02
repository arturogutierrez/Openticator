package com.arturogutierrez.openticator.helpers

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class StringExtTest {
    @Test
    fun shouldIdentifyABase32StringWithOnlyUpperCaseLetters() {
        assertThat("ABCDEFGHIJKLMNOPQRSTUVWXYZ234567".isBase32(), `is`(true))
    }

    @Test
    fun shouldIdentifyABase32StringWithOnlyLowerCaseLetters() {
        assertThat("abcdefghijklmnopqrstuvwxyz234567".isBase32(), `is`(true))
    }

    @Test
    fun shouldIdentifyABase32StringWithUpperAndLowerCaseLetters() {
        assertThat("abcdefghijklmNOPQRSTUVWXYZ234567".isBase32(), `is`(true))
    }

    @Test
    fun shouldNotIdentifyABase32StringWithA1() {
        assertThat("abcdefghijklmNOPQRSTUVWXYZ1234567".isBase32(), `is`(false))
    }

    @Test
    fun shouldNotIdentifyABase32StringWithAn8() {
        assertThat("abcdefghijklmNOPQRSTUVWXYZ2345678".isBase32(), `is`(false))
    }

    @Test
    fun shouldNotIdentifyABase32StringWithASymbol() {
        assertThat("abcdefghijklmNOPQRSTUVWXYZ2345678#".isBase32(), `is`(false))
    }
}