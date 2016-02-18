package com.arturogutierrez.openticator.domain.account.list.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.arturogutierrez.openticator.R;
import com.arturogutierrez.openticator.domain.account.model.Account;
import com.arturogutierrez.openticator.domain.account.model.AccountPasscode;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import rx.Observable;
import rx.subjects.PublishSubject;

public class AccountsAdapter extends RecyclerView.Adapter<AccountViewHolder>
    implements AccountViewHolder.OnClickListener {

  private final LayoutInflater layoutInflater;
  private final Set<Account> selectedAccounts;
  private List<AccountPasscode> accounts;
  private boolean editMode;

  private PublishSubject<Boolean> editModeSubject;
  private PublishSubject<Set<Account>> selectedAccountsSubject;

  public AccountsAdapter(Context context, List<AccountPasscode> accounts) {
    this.layoutInflater = LayoutInflater.from(context);
    this.accounts = accounts;
    this.selectedAccounts = new HashSet<>(accounts.size());
    this.editMode = false;
    this.editModeSubject = PublishSubject.create();
    this.selectedAccountsSubject = PublishSubject.create();
  }

  public void setAccounts(List<AccountPasscode> accounts) {
    this.accounts = accounts;
  }

  public Observable<Boolean> editMode() {
    return editModeSubject;
  }

  public Observable<Set<Account>> selectedAccounts() {
    return selectedAccountsSubject;
  }

  public void setEditMode(boolean editMode) {
    if (this.editMode == editMode) {
      return;
    }

    this.editMode = editMode;
    if (!editMode) {
      clearSelection();
    }

    editModeSubject.onNext(editMode);
  }

  public Set<Account> getSelectedAccounts() {
    return selectedAccounts;
  }

  @Override
  public AccountViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = layoutInflater.inflate(R.layout.layout_account_row, parent, false);
    return new AccountViewHolder(view, this);
  }

  @Override
  public void onBindViewHolder(AccountViewHolder viewHolder, int position) {
    AccountPasscode accountPasscode = accounts.get(position);
    boolean isSelected = selectedAccounts.contains(accountPasscode.getAccount());
    viewHolder.showAccount(accountPasscode, isSelected);
  }

  @Override
  public int getItemCount() {
    return accounts.size();
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
      setEditMode(true);
      selectOrDeselect(position);
    }
  }

  private void selectOrDeselect(int position) {
    AccountPasscode accountPasscode = accounts.get(position);
    Account account = accountPasscode.getAccount();
    if (selectedAccounts.contains(account)) {
      selectedAccounts.remove(account);
    } else {
      selectedAccounts.add(account);
    }
    notifyItemChanged(position);

    selectedAccountsSubject.onNext(selectedAccounts);
  }
}
