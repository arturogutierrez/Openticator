package com.arturogutierrez.openticator.domain.backup

interface SimpleEncryptor {

  fun encrypt(input: String, password: String) : String

  fun decrypt(cipherTextBase64: String, password: String) : String
}