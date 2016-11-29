package com.arturogutierrez.openticator.domain.account.list.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.arturogutierrez.openticator.R
import com.arturogutierrez.openticator.domain.account.list.view.CountdownWidget
import com.arturogutierrez.openticator.domain.account.model.AccountPasscode
import com.arturogutierrez.openticator.domain.issuer.IssuerDecoratorFactory
import com.arturogutierrez.openticator.domain.otp.time.CurrentTimeProvider
import com.arturogutierrez.openticator.domain.otp.time.RemainingTimeCalculator
import org.jetbrains.anko.find
import org.jetbrains.anko.imageResource

class AccountViewHolder(itemView: View, onItemClick: (position: Int) -> Unit,
                        onLongItemClick: (Int) -> Boolean) : RecyclerView.ViewHolder(itemView) {

  private val issuerDecoratorFactory: IssuerDecoratorFactory
  private val ivIcon: ImageView
  private val tvName: TextView
  private val tvPasscode: TextView
  private val vCountdown: CountdownWidget

  init {
    issuerDecoratorFactory = IssuerDecoratorFactory(itemView.context)
    ivIcon = itemView.find<ImageView>(R.id.iv_icon)
    tvName = itemView.find<TextView>(R.id.tv_account_name)
    tvPasscode = itemView.find<TextView>(R.id.tv_code)
    vCountdown = itemView.find<CountdownWidget>(R.id.v_countdown)

    itemView.setOnClickListener { onItemClick(layoutPosition) }
    itemView.setOnLongClickListener { onLongItemClick(layoutPosition) }
  }

  fun showAccount(accountPasscode: AccountPasscode, isSelected: Boolean) {
    val issuerDecorator = issuerDecoratorFactory.create(accountPasscode.issuer)
    ivIcon.imageResource = issuerDecorator.imageResource
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

  fun stopAnimation() = vCountdown.stopAnimation()

  private fun calculateRemainingSeconds(validUntilInSeconds: Long): Int {
    val timeCalculator = RemainingTimeCalculator(CurrentTimeProvider())
    return timeCalculator.calculateRemainingSeconds(validUntilInSeconds)
  }
}
