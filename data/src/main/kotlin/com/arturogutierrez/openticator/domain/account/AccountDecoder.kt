package com.arturogutierrez.openticator.domain.account

import android.net.Uri

import com.arturogutierrez.openticator.domain.account.model.Account
import com.arturogutierrez.openticator.domain.account.model.OTPType
import java.net.URLDecoder
import javax.inject.Inject

class AccountDecoder @Inject constructor(val accountFactory: AccountFactory) {

  private companion object {
    val SCHEME = "otpauth"
    val TOTP = "totp"
    val HOTP = "hotp"
    val SECRET = "secret"
    val ISSUER = "issuer"
  }

  fun decode(accountUri: String): Account? {
    val uri = Uri.parse(accountUri) ?: return null
    val scheme = uri.scheme ?: return null

    if (!scheme.toLowerCase().equals(SCHEME, ignoreCase = true)) {
      return null
    }

    return decodeAccount(uri)
  }

  private fun decodeAccount(uri: Uri): Account? {
    val otpString = uri.authority.toLowerCase()
    val secret = uri.getQueryParameter(SECRET)
    val issuer = uri.getQueryParameter(ISSUER)
    var accountName = getAccountName(uri.path)
    if (!isValidType(otpString) || !isValidSecret(secret) || accountName == null) {
      return null
    }

    val otpType = getOTPType(otpString)
    val accountNamePair = accountName.split(":".toRegex())
    if (accountNamePair.size == 2) {
      if (accountNamePair[0].equals(issuer, ignoreCase = true)) {
        accountName = accountNamePair[1]
      }
    }

    return accountFactory.createAccount(otpType, accountName, secret, issuer)
  }

  private fun isValidType(otpTypeInLowercase: String): Boolean {
    return otpTypeInLowercase == HOTP || otpTypeInLowercase == TOTP
  }

  private fun getOTPType(otpString: String): OTPType {
    if (otpString.equals(HOTP, ignoreCase = true)) {
      return OTPType.HOTP
    }
    return OTPType.TOTP
  }

  private fun isValidSecret(secret: String?): Boolean {
    return secret != null && secret.length > 0
  }

  private fun getAccountName(path: String?): String? {
    val decodedPath = if (path != null) URLDecoder.decode(path, "UTF-8") else return null

    if (!decodedPath.startsWith("/")) {
      return null
    }

    // Remove leading '/'
    val accountName = decodedPath.substring(1).trim { it <= ' ' }
    if (accountName.length == 0) {
      return null
    }

    return accountName
  }
}
