package com.arturogutierrez.openticator.domain.account.list.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.arturogutierrez.openticator.R;
import com.arturogutierrez.openticator.domain.account.model.AccountPasscode;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AccountsAdapter extends RecyclerView.Adapter<AccountViewHolder>
    implements AccountViewHolder.OnClickListener {

  private final LayoutInflater layoutInflater;
  private final Set<AccountPasscode> selectedAccounts;
  private List<AccountPasscode> accounts;
  private boolean editMode;

  public AccountsAdapter(Context context, List<AccountPasscode> accounts) {
    this.layoutInflater = LayoutInflater.from(context);
    this.accounts = accounts;
    this.selectedAccounts = new HashSet<>(accounts.size());
    this.editMode = false;
  }

  @Override
  public AccountViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = layoutInflater.inflate(R.layout.layout_account_row, parent, false);
    return new AccountViewHolder(view, this);
  }

  @Override
  public void onBindViewHolder(AccountViewHolder viewHolder, int position) {
    AccountPasscode accountPasscode = accounts.get(position);
    boolean isSelected = selectedAccounts.contains(accountPasscode);
    viewHolder.showAccount(accountPasscode, isSelected);
  }

  @Override
  public int getItemCount() {
    return accounts.size();
  }

  public void setAccounts(List<AccountPasscode> accounts) {
    this.accounts = accounts;
  }

  public void setEditMode(boolean editMode) {
    this.editMode = editMode;
    if (!editMode) {
      clearSelection();
    }
  }

  private void clearSelection() {
    if (selectedAccounts.size() > 0) {
      selectedAccounts.clear();
      notifyDataSetChanged();
    }
  }

  @Override
  public void onItemClick(int position) {
    if (editMode) {
      selectOrDeselect(position);
    }
  }

  @Override
  public void onLongItemClick(int position) {
    if (!editMode) {
      editMode = true;
      selectOrDeselect(position);
    }
  }

  private void selectOrDeselect(int position) {
    AccountPasscode accountPasscode = accounts.get(position);
    if (selectedAccounts.contains(accountPasscode)) {
      selectedAccounts.remove(accountPasscode);
    } else {
      selectedAccounts.add(accountPasscode);
    }
    notifyItemChanged(position);
  }
}
