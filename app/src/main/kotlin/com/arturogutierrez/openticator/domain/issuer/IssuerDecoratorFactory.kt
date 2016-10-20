package com.arturogutierrez.openticator.domain.issuer

import android.content.Context
import com.arturogutierrez.openticator.R
import com.arturogutierrez.openticator.di.PerActivity
import com.arturogutierrez.openticator.domain.issuer.model.Issuer
import java.util.*
import javax.inject.Inject

@PerActivity
class IssuerDecoratorFactory @Inject constructor(val context: Context) {

  private val names: Map<Issuer, String>
  private val images: Map<Issuer, Int>

  init {
    names = mapOf(
        Issuer.AWS to context.getString(R.string.aws),
        Issuer.DIGITALOCEAN to context.getString(R.string.digital_ocean),
        Issuer.BITCOIN to context.getString(R.string.bitcoin),
        Issuer.DROPBOX to context.getString(R.string.dropbox),
        Issuer.EVERNOTE to context.getString(R.string.evernote),
        Issuer.FACEBOOK to context.getString(R.string.facebook),
        Issuer.GITHUB to context.getString(R.string.github),
        Issuer.GOOGLE to context.getString(R.string.google),
        Issuer.MICROSOFT to context.getString(R.string.microsoft),
        Issuer.SLACK to context.getString(R.string.slack),
        Issuer.WORDPRESS to context.getString(R.string.wordpress)

    )
    images = mapOf(
        Issuer.AWS to R.drawable.icon_account_aws,
        Issuer.DIGITALOCEAN to R.drawable.icon_account_digitalocean,
        Issuer.BITCOIN to R.drawable.icon_account_bitcoin,
        Issuer.DROPBOX to R.drawable.icon_account_dropbox,
        Issuer.EVERNOTE to R.drawable.icon_account_evernote,
        Issuer.FACEBOOK to R.drawable.icon_account_facebook,
        Issuer.GITHUB to R.drawable.icon_account_github,
        Issuer.GOOGLE to R.drawable.icon_account_google,
        Issuer.MICROSOFT to R.drawable.icon_account_microsoft,
        Issuer.SLACK to R.drawable.icon_account_slack,
        Issuer.WORDPRESS to R.drawable.icon_account_wordpress)
  }

  fun create(issuers: List<Issuer>): List<IssuerDecorator> {
    val issuerDecorators = ArrayList<IssuerDecorator>(issuers.size)
    for (issuer in issuers) {
      val issuerDecorator = create(issuer)
      issuerDecorators.add(issuerDecorator)
    }
    return issuerDecorators
  }

  private fun create(issuer: Issuer): IssuerDecorator {
    val name = names[issuer]!!
    val image = images[issuer]!!
    return IssuerDecorator(issuer, name, image)
  }
}
