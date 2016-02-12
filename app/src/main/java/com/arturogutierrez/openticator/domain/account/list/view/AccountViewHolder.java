package com.arturogutierrez.openticator.domain.account.list.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.arturogutierrez.openticator.R;
import com.arturogutierrez.openticator.domain.account.model.AccountPasscode;

public class AccountViewHolder extends RecyclerView.ViewHolder {

  @Bind(R.id.tv_account_name)
  TextView tvName;
  @Bind(R.id.tv_code)
  TextView tvPasscode;
  @Bind(R.id.v_countdown)
  CountdownWidget vCountdown;

  public AccountViewHolder(View itemView) {
    super(itemView);

    ButterKnife.bind(this, itemView);
  }

  public void showAccount(AccountPasscode accountPasscode) {
    tvName.setText(accountPasscode.getAccountName());
    tvPasscode.setText(accountPasscode.getPasscode());
    startAnimation();
  }

  public void startAnimation() {
    vCountdown.startAnimation(30);
  }

  public void stopAniation() {
    vCountdown.stopAnimation();
    ;
  }
}
