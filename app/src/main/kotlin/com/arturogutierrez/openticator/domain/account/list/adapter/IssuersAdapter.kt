package com.arturogutierrez.openticator.domain.account.list.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.arturogutierrez.openticator.R
import com.arturogutierrez.openticator.domain.issuer.IssuerDecorator
import com.arturogutierrez.openticator.domain.issuer.model.Issuer

class IssuersAdapter(val layoutInflater: LayoutInflater, val issuers: List<IssuerDecorator>,
                     val onSelectedItem: (Issuer) -> Unit) : RecyclerView.Adapter<IssuersViewHolder>(), IssuersViewHolder.OnClickListener {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssuersViewHolder {
    val view = layoutInflater.inflate(R.layout.dialog_issuers_row, parent, false)
    return IssuersViewHolder(view, this)
  }

  override fun onBindViewHolder(holder: IssuersViewHolder, position: Int) {
    val issuerDecorator = issuers[position]
    holder.showIssuer(issuerDecorator)
  }

  override fun getItemCount(): Int {
    return issuers.size
  }

  override fun onItemClick(position: Int) {
    val issuer = issuers[position].issuer
    onSelectedItem(issuer)
  }
}
