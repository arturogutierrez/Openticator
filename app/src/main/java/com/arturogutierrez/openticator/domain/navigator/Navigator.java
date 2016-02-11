package com.arturogutierrez.openticator.domain.navigator;

import android.content.Context;
import android.content.Intent;
import com.arturogutierrez.openticator.domain.account.add.activity.AddAccountManuallyActivity;
import javax.inject.Inject;

public class Navigator {

  @Inject
  public Navigator() {

  }

  public void navigateToAddAccountManually(Context context) {
    Intent intent = new Intent(context, AddAccountManuallyActivity.class);
    context.startActivity(intent);
  }
}
