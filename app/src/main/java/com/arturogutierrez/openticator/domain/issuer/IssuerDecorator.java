package com.arturogutierrez.openticator.domain.issuer;

import com.arturogutierrez.openticator.domain.issuer.model.Issuer;

public class IssuerDecorator {

  private final Issuer issuer;
  private final String name;
  private final int imageResource;

  public IssuerDecorator(Issuer issuer, String name, int imageResource) {
    this.issuer = issuer;
    this.name = name;
    this.imageResource = imageResource;
  }

  public Issuer getIssuer() {
    return issuer;
  }

  public String getName() {
    return name;
  }

  public int getImageResource() {
    return imageResource;
  }
}
