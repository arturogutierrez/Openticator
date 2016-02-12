package com.arturogutierrez.openticator.domain.account.model;

public enum Issuer {

  UNKNOWN(Constants.ID_UNKNOWN),
  GOOGLE(Constants.ID_GOOGLE);

  private final String identifier;

  Issuer(String identifier) {
    this.identifier = identifier;
  }

  public String getIdentifier() {
    return identifier;
  }

  public static Issuer fromString(String identifier) {
    if (identifier == null) {
      return UNKNOWN;
    }

    if (identifier.equals(Constants.ID_GOOGLE)) {
      return GOOGLE;
    }

    return UNKNOWN;
  }

  private static class Constants {
    private static final String ID_UNKNOWN = "unknown";
    private static final String ID_GOOGLE = "google";
  }
}
