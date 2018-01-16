package com.arturogutierrez.openticator.domain.issuer

import com.arturogutierrez.openticator.domain.issuer.model.Issuer
import java.util.*
import java.util.regex.Pattern
import javax.inject.Inject

class IssuerDecoder @Inject constructor() {

  private val expressions: EnumMap<Issuer, String> = EnumMap<Issuer, String>(Issuer::class.java)

  init {
    expressions.put(Issuer.AWS, "mfa-device")
    expressions.put(Issuer.BITCOIN, "bitcoin")
    expressions.put(Issuer.DIGITALOCEAN, "digital\\s?ocean")
    expressions.put(Issuer.DROPBOX, "dropbox")
    expressions.put(Issuer.EVERNOTE, "evernote")
    expressions.put(Issuer.FACEBOOK, "facebook")
    expressions.put(Issuer.GITHUB, "github")
    expressions.put(Issuer.GOOGLE, "google")
    expressions.put(Issuer.MICROSOFT, "microsoft")
    expressions.put(Issuer.SLACK, "slack")
    expressions.put(Issuer.WORDPRESS, "wordpress")
    expressions.put(Issuer.ROBERTSPACEINDUSTRIES, "roberts\\s?space\\s?industries")
  }

  fun decode(accountName: String, issuerText: String): Issuer {
    val issuer = decode(issuerText.toLowerCase())
    if (issuer != Issuer.UNKNOWN) {
      return issuer
    }

    return decode(accountName)
  }

  private fun decode(text: String): Issuer {
    if (text.isEmpty()) {
      return Issuer.UNKNOWN
    }

    for ((key, value) in expressions) {
      if (applyRegularExpression(text, value)) {
        return key
      }
    }
    return Issuer.UNKNOWN
  }

  private fun applyRegularExpression(issuer: String, regularExpression: String): Boolean {
    val pattern = Pattern.compile(regularExpression)
    val matcher = pattern.matcher(issuer)
    return matcher.find()
  }
}
