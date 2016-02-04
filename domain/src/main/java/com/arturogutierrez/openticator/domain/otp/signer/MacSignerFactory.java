package com.arturogutierrez.openticator.domain.otp.signer;

import com.arturogutierrez.openticator.domain.helper.Base32String;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.inject.Inject;

public class MacSignerFactory {

  @Inject
  public MacSignerFactory() {

  }

  public MacSigner createMacSigner(String secret) throws IllegalArgumentException {
    try {
      byte[] secretBase32 = Base32String.decode(secret);

      Mac mac = Mac.getInstance("HMACSHA1");
      mac.init(new SecretKeySpec(secretBase32, ""));

      return new MacSigner(mac);
    } catch (Exception e) {
      throw new IllegalArgumentException(e.getMessage());
    }
  }
}
