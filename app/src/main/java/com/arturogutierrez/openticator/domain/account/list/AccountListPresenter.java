package com.arturogutierrez.openticator.domain.account.list;

import com.arturogutierrez.openticator.domain.account.interactor.GetAccountsInteractor;
import com.arturogutierrez.openticator.domain.account.model.Account;
import com.arturogutierrez.openticator.domain.account.model.AccountPasscode;
import com.arturogutierrez.openticator.domain.otp.OneTimePassword;
import com.arturogutierrez.openticator.domain.otp.OneTimePasswordFactory;
import com.arturogutierrez.openticator.interactor.DefaultSubscriber;
import com.arturogutierrez.openticator.view.presenter.Presenter;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class AccountListPresenter extends DefaultSubscriber<List<Account>> implements Presenter {

  private final GetAccountsInteractor getAccountsInteractorInteractor;
  private final OneTimePasswordFactory oneTimePasswordFactory;
  private AccountListView view;

  @Inject
  public AccountListPresenter(GetAccountsInteractor getAccountsInteractorInteractor,
      OneTimePasswordFactory oneTimePasswordFactory) {
    this.getAccountsInteractorInteractor = getAccountsInteractorInteractor;
    this.oneTimePasswordFactory = oneTimePasswordFactory;
  }

  public void setView(AccountListView view) {
    this.view = view;
  }

  @Override
  public void resume() {
    getAccountsInteractorInteractor.execute(this);
  }

  @Override
  public void pause() {
    getAccountsInteractorInteractor.unsubscribe();
  }

  @Override
  public void destroy() {
    getAccountsInteractorInteractor.unsubscribe();
  }

  @Override
  public void onNext(List<Account> accounts) {
    if (accounts == null || accounts.size() == 0) {
      view.viewNoItems();
      return;
    }

    List<AccountPasscode> accountPasscodes = calculatePasscodes(accounts);
    view.renderAccounts(accountPasscodes);
  }

  @Override
  public void onError(Throwable e) {
    view.viewNoItems();
  }

  private List<AccountPasscode> calculatePasscodes(List<Account> accounts) {
    List<AccountPasscode> accountPasscodes = new ArrayList<>(accounts.size());
    for (Account account : accounts) {
      AccountPasscode accountPasscode = calculatePasscode(account);
      accountPasscodes.add(accountPasscode);
    }
    return accountPasscodes;
  }

  private AccountPasscode calculatePasscode(Account account) {
    // TODO: Pick right delta offset time
    OneTimePassword oneTimePassword = oneTimePasswordFactory.createOneTimePassword(account, 0);
    String passcode = oneTimePassword.generate();
    return new AccountPasscode(account.getName(), account.getIssuer(), passcode,
        AccountPasscode.INFINITE);
  }
}
