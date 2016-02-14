package com.arturogutierrez.openticator.domain.account.list.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.arturogutierrez.openticator.R;
import com.arturogutierrez.openticator.domain.account.model.AccountPasscode;
import com.arturogutierrez.openticator.domain.otp.time.CurrentTimeProvider;
import com.arturogutierrez.openticator.domain.otp.time.RemainingTimeCalculator;

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
    tvPasscode.setText(accountPasscode.getPasscode().getCode());

    int animationLength =
        calculateRemainingSeconds(accountPasscode.getPasscode().getValidUntilInSeconds());
    startAnimation(animationLength);
  }

  private void startAnimation(int animationLength) {
    // TODO: Pass valid window length in Passcode
    float percentAnimation = animationLength / 30.0f;
    vCountdown.startAnimation(animationLength, percentAnimation);
  }

  public void stopAnimation() {
    vCountdown.stopAnimation();
  }

  private int calculateRemainingSeconds(long validUntilInSeconds) {
    RemainingTimeCalculator timeCalculator = new RemainingTimeCalculator(new CurrentTimeProvider());
    return timeCalculator.calculateRemainingSeconds(validUntilInSeconds);
  }
}
