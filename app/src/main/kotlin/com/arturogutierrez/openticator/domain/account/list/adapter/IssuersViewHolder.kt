package com.arturogutierrez.openticator.domain.account.list.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.arturogutierrez.openticator.R
import com.arturogutierrez.openticator.domain.issuer.IssuerDecorator

class IssuersViewHolder(itemView: View, onItemClick: (Int) -> Unit) : RecyclerView.ViewHolder(itemView) {

  private val tvName: TextView
  private val ivIcon: ImageView

  init {
    tvName = itemView.findViewById(R.id.tv_name) as TextView
    ivIcon = itemView.findViewById(R.id.iv_icon) as ImageView
    itemView.setOnClickListener { onItemClick(layoutPosition) }
  }

  fun showIssuer(issuerDecorator: IssuerDecorator) {
    tvName.text = issuerDecorator.name
    ivIcon.setImageResource(issuerDecorator.imageResource)
  }
}
