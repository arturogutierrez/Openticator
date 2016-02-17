package com.arturogutierrez.openticator.domain.issuer;

import com.arturogutierrez.openticator.domain.issuer.model.Issuer;
import java.util.EnumMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.inject.Inject;

public class IssuerDecoder {

  private final EnumMap<Issuer, String> expressions;

  @Inject
  public IssuerDecoder() {
    expressions = new EnumMap<>(Issuer.class);
    expressions.put(Issuer.AWS, "mfa-device");
    expressions.put(Issuer.BITCOIN, "bitcoin");
    expressions.put(Issuer.DIGITALOCEAN, "digital\\s?ocean");
    expressions.put(Issuer.DROPBOX, "dropbox");
    expressions.put(Issuer.EVERNOTE, "evernote");
    expressions.put(Issuer.FACEBOOK, "facebook");
    expressions.put(Issuer.GITHUB, "github");
    expressions.put(Issuer.GOOGLE, "google");
    expressions.put(Issuer.MICROSOFT, "microsoft");
    expressions.put(Issuer.SLACK, "slack");
    expressions.put(Issuer.WORDPRESS, "wordpress");
  }

  public Issuer decode(String accountName, String issuerText) {
    Issuer issuer = decode(issuerText);
    if (issuer != Issuer.UNKNOWN) {
      return issuer;
    }

    return decode(accountName);
  }

  private Issuer decode(String text) {
    if (text == null || text.length() == 0) {
      return Issuer.UNKNOWN;
    }

    for (Map.Entry<Issuer, String> entry : expressions.entrySet()) {
      if (applyRegularExpression(text, entry.getValue())) {
        return entry.getKey();
      }
    }
    return Issuer.UNKNOWN;
  }

  private boolean applyRegularExpression(String issuer, String regularExpression) {
    Pattern pattern = Pattern.compile(regularExpression);
    Matcher matcher = pattern.matcher(issuer);
    return matcher.find();
  }
}
