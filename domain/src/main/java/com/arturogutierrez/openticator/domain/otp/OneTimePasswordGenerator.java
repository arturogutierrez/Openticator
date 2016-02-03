package com.arturogutierrez.openticator.domain.otp;

import java.nio.ByteBuffer;

class OneTimePasswordGenerator {

  private final int passwordLength;
  private final MacSigner macSigner;

  public OneTimePasswordGenerator(String secret, int passwordLength) {
    this.passwordLength = passwordLength;
    this.macSigner = createMacSigner(secret);
  }

  public String generate(long state) {
    byte[] stateInBytes = ByteBuffer.allocate(Long.BYTES).putLong(state).array();
    return generate(stateInBytes);
  }

  private MacSigner createMacSigner(String secret) {
    MacSignerFactory macSignerFactory = new MacSignerFactory();
    return macSignerFactory.createMacSigner(secret);
  }

  private String generate(byte[] stateInBytes) {
    byte[] stateHash = macSigner.sign(stateInBytes);

    // offset is the low order bits of the last byte of the hash
    int offset = stateHash[stateHash.length - 1] & 0xF;
    // Grab a positive integer value starting at the given offset.
    int truncatedHash = byteArrayToInt(stateHash, offset) & 0x7FFFFFFF;
    int pinValue = truncatedHash % (int) Math.pow(10, passwordLength);

    return String.format("%0" + passwordLength + "d", pinValue);
  }

  private int byteArrayToInt(byte[] bytes, int offset) {
    int value = 0;
    for (int i = 0; i < Integer.BYTES; i++) {
      int shift = (Integer.BYTES - 1 - i) * 8;
      value += (bytes[i + offset] & 0xFF) << shift;
    }
    return value;
  }
}
