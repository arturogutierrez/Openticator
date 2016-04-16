package com.arturogutierrez.openticator.domain.sync;

import com.arturogutierrez.openticator.domain.account.model.Account;
import com.arturogutierrez.openticator.domain.account.repository.AccountRepository;
import java.util.List;
import javax.inject.Inject;

public class AccountsImporter {

  private final AccountRepository accountRepository;

  @Inject
  public AccountsImporter(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  public int importAccounts(List<Account> accounts) {
    // TODO: Import accounts
    return 0;
  }
}
