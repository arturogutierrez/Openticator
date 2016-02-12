package com.arturogutierrez.openticator.domain.account.list.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.arturogutierrez.openticator.R;
import com.arturogutierrez.openticator.domain.account.model.AccountPasscode;
import java.util.List;

public class AccountsAdapter extends RecyclerView.Adapter<AccountViewHolder> {

  private final LayoutInflater layoutInflater;
  private List<AccountPasscode> accounts;

  public AccountsAdapter(Context context, List<AccountPasscode> accounts) {
    this.layoutInflater = LayoutInflater.from(context);
    this.accounts = accounts;
  }

  @Override
  public AccountViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = layoutInflater.inflate(R.layout.layout_account_row, parent, false);
    return new AccountViewHolder(view);
  }

  @Override
  public void onBindViewHolder(AccountViewHolder viewHolder, int position) {
    AccountPasscode account = accounts.get(position);
    viewHolder.showAccount(account);
  }

  @Override
  public int getItemCount() {
    return accounts.size();
  }

  public void setAccounts(List<AccountPasscode> accounts) {
    this.accounts = accounts;
  }
}
