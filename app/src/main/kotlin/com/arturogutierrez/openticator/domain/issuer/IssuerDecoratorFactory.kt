package com.arturogutierrez.openticator.domain.issuer

import android.content.Context
import com.arturogutierrez.openticator.R
import com.arturogutierrez.openticator.di.PerActivity
import com.arturogutierrez.openticator.domain.issuer.model.Issuer
import javax.inject.Inject

@PerActivity
class IssuerDecoratorFactory @Inject constructor(val context: Context) {

  fun create(issuer: Issuer): IssuerDecorator {
    return when (issuer) {
      Issuer.AWS -> IssuerDecorator(issuer, context.getString(R.string.aws), R.drawable.icon_account_aws)
      Issuer.BITCOIN -> IssuerDecorator(issuer, context.getString(R.string.bitcoin), R.drawable.icon_account_bitcoin)
      Issuer.DIGITALOCEAN -> IssuerDecorator(issuer, context.getString(R.string.digital_ocean), R.drawable.icon_account_digitalocean)
      Issuer.DROPBOX -> IssuerDecorator(issuer, context.getString(R.string.dropbox), R.drawable.icon_account_dropbox)
      Issuer.EVERNOTE -> IssuerDecorator(issuer, context.getString(R.string.evernote), R.drawable.icon_account_evernote)
      Issuer.FACEBOOK -> IssuerDecorator(issuer, context.getString(R.string.facebook), R.drawable.icon_account_facebook)
      Issuer.GITHUB -> IssuerDecorator(issuer, context.getString(R.string.github), R.drawable.icon_account_github)
      Issuer.GOOGLE -> IssuerDecorator(issuer, context.getString(R.string.google), R.drawable.icon_account_google)
      Issuer.MICROSOFT -> IssuerDecorator(issuer, context.getString(R.string.microsoft), R.drawable.icon_account_microsoft)
      Issuer.SLACK -> IssuerDecorator(issuer, context.getString(R.string.slack), R.drawable.icon_account_slack)
      Issuer.ROBERTSPACEINDUSTRIES -> IssuerDecorator(issuer, context.getString(R.string.roberts_space_industries), R.drawable.icon_account_rsi)
      Issuer.WORDPRESS -> IssuerDecorator(issuer, context.getString(R.string.wordpress), R.drawable.icon_account_wordpress)
      Issuer.UNKNOWN -> IssuerDecorator(issuer, context.getString(R.string.unknown), R.mipmap.ic_launcher)
    }
  }

  fun create(issuers: List<Issuer>): List<IssuerDecorator> {
    return issuers.map { create(it) }
  }
}
