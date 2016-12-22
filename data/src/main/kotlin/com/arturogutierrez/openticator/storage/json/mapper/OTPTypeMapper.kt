package com.arturogutierrez.openticator.storage.json.mapper

import com.arturogutierrez.openticator.domain.account.model.OTPType
import com.arturogutierrez.openticator.mapper.Mapper
import javax.inject.Inject

class OTPTypeMapper @Inject constructor() : Mapper<OTPType, String> {

  companion object {
    val HOTP = "HOTP"
    val TOTP = "TOTP"
  }

  override fun transform(value: OTPType) = when (value) {
    OTPType.TOTP -> TOTP
    OTPType.HOTP -> HOTP
  }

  override fun reverseTransform(value: String): OTPType {
    if (value == HOTP) {
      return OTPType.HOTP
    }

    return OTPType.TOTP
  }
}
