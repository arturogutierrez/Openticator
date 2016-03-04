package com.arturogutierrez.openticator.domain.issuer;

import android.content.Context;
import com.arturogutierrez.openticator.R;
import com.arturogutierrez.openticator.di.PerActivity;
import com.arturogutierrez.openticator.domain.issuer.model.Issuer;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import javax.inject.Inject;

@PerActivity
public class IssuerDecoratorFactory {

  private final Context context;
  private EnumMap<Issuer, String> names;
  private EnumMap<Issuer, Integer> images;

  @Inject
  public IssuerDecoratorFactory(Context context) {
    this.context = context;
    this.names = new EnumMap<>(Issuer.class);
    this.images = new EnumMap<>(Issuer.class);

    initialize();
  }

  private void initialize() {
    names.put(Issuer.AWS, context.getString(R.string.aws));
    images.put(Issuer.AWS, R.drawable.icon_account_aws);
    names.put(Issuer.DIGITALOCEAN, context.getString(R.string.digital_ocean));
    images.put(Issuer.DIGITALOCEAN, R.drawable.icon_account_digitalocean);
    names.put(Issuer.BITCOIN, context.getString(R.string.bitcoin));
    images.put(Issuer.BITCOIN, R.drawable.icon_account_bitcoin);
    names.put(Issuer.DROPBOX, context.getString(R.string.dropbox));
    images.put(Issuer.DROPBOX, R.drawable.icon_account_dropbox);
    names.put(Issuer.EVERNOTE, context.getString(R.string.evernote));
    images.put(Issuer.EVERNOTE, R.drawable.icon_account_evernote);
    names.put(Issuer.FACEBOOK, context.getString(R.string.facebook));
    images.put(Issuer.FACEBOOK, R.drawable.icon_account_facebook);
    names.put(Issuer.GITHUB, context.getString(R.string.github));
    images.put(Issuer.GITHUB, R.drawable.icon_account_github);
    names.put(Issuer.GOOGLE, context.getString(R.string.google));
    images.put(Issuer.GOOGLE, R.drawable.icon_account_google);
    names.put(Issuer.MICROSOFT, context.getString(R.string.microsoft));
    images.put(Issuer.MICROSOFT, R.drawable.icon_account_microsoft);
    names.put(Issuer.SLACK, context.getString(R.string.slack));
    images.put(Issuer.SLACK, R.drawable.icon_account_slack);
    names.put(Issuer.WORDPRESS, context.getString(R.string.wordpress));
    images.put(Issuer.WORDPRESS, R.drawable.icon_account_wordpress);
  }

  public List<IssuerDecorator> create(List<Issuer> issuers) {
    List<IssuerDecorator> issuerDecorators = new ArrayList<>(issuers.size());
    for (Issuer issuer : issuers) {
      IssuerDecorator issuerDecorator = create(issuer);
      issuerDecorators.add(issuerDecorator);
    }
    return issuerDecorators;
  }

  private IssuerDecorator create(Issuer issuer) {
    String name = names.get(issuer);
    Integer image = images.get(issuer);
    return new IssuerDecorator(issuer, name, image);
  }
}
