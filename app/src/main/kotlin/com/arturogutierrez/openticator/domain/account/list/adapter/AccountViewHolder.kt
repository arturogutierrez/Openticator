package com.arturogutierrez.openticator.domain.account.list.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.arturogutierrez.openticator.R
import com.arturogutierrez.openticator.domain.account.list.view.CountdownWidget
import com.arturogutierrez.openticator.domain.account.model.AccountPasscode
import com.arturogutierrez.openticator.domain.issuer.model.Issuer
import com.arturogutierrez.openticator.domain.otp.time.CurrentTimeProvider
import com.arturogutierrez.openticator.domain.otp.time.RemainingTimeCalculator
import org.jetbrains.anko.find
import org.jetbrains.anko.imageResource

class AccountViewHolder(itemView: View, onItemClick: (position: Int) -> Unit,
                        onLongItemClick: (Int) -> Boolean) : RecyclerView.ViewHolder(itemView) {

  private val ivIcon: ImageView
  private val tvName: TextView
  private val tvPasscode: TextView
  private val vCountdown: CountdownWidget

  init {
    ivIcon = itemView.find<ImageView>(R.id.iv_icon)
    tvName = itemView.find<TextView>(R.id.tv_account_name)
    tvPasscode = itemView.find<TextView>(R.id.tv_code)
    vCountdown = itemView.find<CountdownWidget>(R.id.v_countdown)

    itemView.setOnClickListener { onItemClick(layoutPosition) }
    itemView.setOnLongClickListener { onLongItemClick(layoutPosition) }
  }

  fun showAccount(accountPasscode: AccountPasscode, isSelected: Boolean) {
    ivIcon.imageResource = getAccountIconDrawableResource(accountPasscode.issuer)
    tvName.text = accountPasscode.account.name
    tvPasscode.text = accountPasscode.passcode.code
    itemView.isSelected = isSelected

    val animationLength = calculateRemainingSeconds(accountPasscode.passcode.validUntil)
    startAnimation(animationLength)
  }

  private fun startAnimation(animationLength: Int) {
    // TODO: Pass valid window length in Passcode
    val percentAnimation = animationLength / 30.0f
    vCountdown.startAnimation(animationLength, percentAnimation)
  }

  fun stopAnimation() {
    vCountdown.stopAnimation()
  }

  private fun calculateRemainingSeconds(validUntilInSeconds: Long): Int {
    val timeCalculator = RemainingTimeCalculator(CurrentTimeProvider())
    return timeCalculator.calculateRemainingSeconds(validUntilInSeconds)
  }

  // TODO: Duplicated in IssuerDecoratorFactory
  private fun getAccountIconDrawableResource(issuer: Issuer): Int {
    when (issuer) {
      Issuer.AWS -> return R.drawable.icon_account_aws
      Issuer.BITCOIN -> return R.drawable.icon_account_bitcoin
      Issuer.DIGITALOCEAN -> return R.drawable.icon_account_digitalocean
      Issuer.DROPBOX -> return R.drawable.icon_account_dropbox
      Issuer.EVERNOTE -> return R.drawable.icon_account_evernote
      Issuer.FACEBOOK -> return R.drawable.icon_account_facebook
      Issuer.GITHUB -> return R.drawable.icon_account_github
      Issuer.GOOGLE -> return R.drawable.icon_account_google
      Issuer.MICROSOFT -> return R.drawable.icon_account_microsoft
      Issuer.SLACK -> return R.drawable.icon_account_slack
      Issuer.WORDPRESS -> return R.drawable.icon_account_wordpress
      else -> return R.mipmap.ic_launcher
    }
  }
}
