package com.arturogutierrez.openticator.domain.account.list.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.arturogutierrez.openticator.R;
import com.arturogutierrez.openticator.domain.account.model.AccountPasscode;
import com.arturogutierrez.openticator.domain.issuer.model.Issuer;
import com.arturogutierrez.openticator.domain.otp.time.CurrentTimeProvider;
import com.arturogutierrez.openticator.domain.otp.time.RemainingTimeCalculator;

public class AccountViewHolder extends RecyclerView.ViewHolder {

  @Bind(R.id.iv_icon)
  ImageView ivIcon;
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
    ivIcon.setImageResource(getAccountIconDrawableResource(accountPasscode.getIssuer()));
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

  private int getAccountIconDrawableResource(Issuer issuer) {
    switch (issuer) {
      case AWS:
        return R.drawable.icon_account_aws;
      case BITCOIN:
        return R.drawable.icon_account_bitcoin;
      case DIGITALOCEAN:
        return R.drawable.icon_account_digitalocean;
      case DROPBOX:
        return R.drawable.icon_account_dropbox;
      case EVERNOTE:
        return R.drawable.icon_account_evernote;
      case FACEBOOK:
        return R.drawable.icon_account_facebook;
      case GITHUB:
        return R.drawable.icon_account_github;
      case GOOGLE:
        return R.drawable.icon_account_google;
      case MICROSOFT:
        return R.drawable.icon_account_microsoft;
      case SLACK:
        return R.drawable.icon_account_slack;
      case WORDPRESS:
        return R.drawable.icon_account_wordpress;
      default:
        return R.mipmap.ic_launcher;
    }
  }
}
