package com.arturogutierrez.openticator.domain.account.list.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.arturogutierrez.openticator.R;
import com.arturogutierrez.openticator.domain.issuer.IssuerDecorator;

public class IssuersViewHolder extends RecyclerView.ViewHolder {

  interface OnClickListener {

    void onItemClick(int position);
  }

  @Bind(R.id.tv_name)
  TextView tvName;
  @Bind(R.id.iv_icon)
  ImageView ivIcon;

  public IssuersViewHolder(View itemView, OnClickListener onClickListener) {
    super(itemView);

    itemView.setOnClickListener(l -> onClickListener.onItemClick(getLayoutPosition()));

    ButterKnife.bind(this, itemView);
  }

  public void showIssuer(IssuerDecorator issuerDecorator) {
    tvName.setText(issuerDecorator.getName());
    ivIcon.setImageResource(issuerDecorator.getImageResource());
  }
}
