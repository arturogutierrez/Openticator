package com.arturogutierrez.openticator.domain.account.list;

import com.arturogutierrez.openticator.domain.account.model.AccountPasscode;
import java.util.List;

public interface AccountListView {

  void viewNoItems();

  void renderAccounts(List<AccountPasscode> accounts);

  void showEditActionButtons();
}
