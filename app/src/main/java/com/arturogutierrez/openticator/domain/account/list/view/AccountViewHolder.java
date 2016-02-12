package com.arturogutierrez.openticator.domain.account.list.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.arturogutierrez.openticator.R;
import com.arturogutierrez.openticator.domain.account.model.Account;

public class AccountViewHolder extends RecyclerView.ViewHolder {

  @Bind(R.id.tv_account_name)
  TextView tvName;

  public AccountViewHolder(View itemView) {
    super(itemView);

    ButterKnife.bind(this, itemView);
  }

  public void showAccount(Account account) {
    tvName.setText(account.getName());
  }
}
