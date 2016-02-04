package com.arturogutierrez.openticator.domain.otp.signer;

import javax.crypto.Mac;

public class MacSigner {

  private final Mac mac;

  public MacSigner(Mac mac) {
    this.mac = mac;
  }

  public byte[] sign(byte[] dataToSign) {
    return mac.doFinal(dataToSign);
  }
}
