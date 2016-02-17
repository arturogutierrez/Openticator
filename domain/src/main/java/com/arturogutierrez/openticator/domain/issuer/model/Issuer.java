package com.arturogutierrez.openticator.domain.issuer.model;

public enum Issuer {

  UNKNOWN(Constants.ID_UNKNOWN),
  AWS(Constants.ID_AWS),
  BITCOIN(Constants.ID_BITCOIN),
  DIGITALOCEAN(Constants.ID_DIGITALOCEAN),
  DROPBOX(Constants.ID_DROPBOX),
  EVERNOTE(Constants.ID_EVERNOTE),
  FACEBOOK(Constants.ID_FACEBOOK),
  GITHUB(Constants.ID_GITHUB),
  GOOGLE(Constants.ID_GOOGLE),
  MICROSOFT(Constants.ID_MICROSOFT),
  SLACK(Constants.ID_SLACK),
  WORDPRESS(Constants.ID_WORDPRESS);

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

    // TODO: Move to hash map
    if (identifier.equals(Constants.ID_AWS)) {
      return AWS;
    } else if (identifier.equals(Constants.ID_BITCOIN)) {
      return BITCOIN;
    } else if (identifier.equals(Constants.ID_DIGITALOCEAN)) {
      return DIGITALOCEAN;
    } else if (identifier.equals(Constants.ID_DROPBOX)) {
      return DROPBOX;
    } else if (identifier.equals(Constants.ID_EVERNOTE)) {
      return EVERNOTE;
    } else if (identifier.equals(Constants.ID_FACEBOOK)) {
      return FACEBOOK;
    } else if (identifier.equals(Constants.ID_GITHUB)) {
      return GITHUB;
    } else if (identifier.equals(Constants.ID_GOOGLE)) {
      return GOOGLE;
    } else if (identifier.equals(Constants.ID_MICROSOFT)) {
      return MICROSOFT;
    } else if (identifier.equals(Constants.ID_SLACK)) {
      return SLACK;
    } else if (identifier.equals(Constants.ID_WORDPRESS)) {
      return WORDPRESS;
    }

    return UNKNOWN;
  }

  private static class Constants {
    private static final String ID_UNKNOWN = "unknown";
    private static final String ID_AWS = "aws";
    private static final String ID_BITCOIN = "bitcoin";
    private static final String ID_DIGITALOCEAN = "digitalocean";
    private static final String ID_DROPBOX = "dropbox";
    private static final String ID_EVERNOTE = "evernote";
    private static final String ID_FACEBOOK = "facebook";
    private static final String ID_GITHUB = "github";
    private static final String ID_GOOGLE = "google";
    private static final String ID_MICROSOFT = "microsoft";
    private static final String ID_SLACK = "slack";
    private static final String ID_WORDPRESS = "wordpress";
  }
}
