package com.arturogutierrez.openticator.domain.account.interactor;

import com.arturogutierrez.openticator.domain.account.model.Account;
import com.arturogutierrez.openticator.domain.account.model.AccountPasscode;
import com.arturogutierrez.openticator.domain.account.repository.AccountRepository;
import com.arturogutierrez.openticator.domain.category.CategoryFactory;
import com.arturogutierrez.openticator.domain.category.CategorySelector;
import com.arturogutierrez.openticator.domain.category.model.Category;
import com.arturogutierrez.openticator.domain.otp.OneTimePassword;
import com.arturogutierrez.openticator.domain.otp.OneTimePasswordFactory;
import com.arturogutierrez.openticator.domain.otp.model.Passcode;
import com.arturogutierrez.openticator.executor.PostExecutionThread;
import com.arturogutierrez.openticator.executor.ThreadExecutor;
import com.arturogutierrez.openticator.interactor.Interactor;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.functions.Func1;

public class GetAccountPasscodesInteractor extends Interactor<List<AccountPasscode>> {

  private final CategorySelector categorySelector;
  private final CategoryFactory categoryFactory;
  private final AccountRepository accountRepository;
  private final OneTimePasswordFactory oneTimePasswordFactory;

  public GetAccountPasscodesInteractor(CategorySelector categorySelector,
      CategoryFactory categoryFactory, AccountRepository accountRepository,
      OneTimePasswordFactory oneTimePasswordFactory, ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.categorySelector = categorySelector;
    this.categoryFactory = categoryFactory;
    this.accountRepository = accountRepository;
    this.oneTimePasswordFactory = oneTimePasswordFactory;
  }

  @Override
  public Observable<List<AccountPasscode>> createObservable() {
    return categorySelector.getSelectedCategory()
        .flatMap(new Func1<Category, Observable<List<Account>>>() {
          @Override
          public Observable<List<Account>> call(Category category) {
            Category emptyCategory = categoryFactory.createEmptyCategory();
            if (category.equals(emptyCategory)) {
              return accountRepository.getAllAccounts();
            }
            return accountRepository.getAccounts(category);
          }
        })
        .map(new Func1<List<Account>, List<AccountPasscode>>() {
          @Override
          public List<AccountPasscode> call(List<Account> accountList) {
            return GetAccountPasscodesInteractor.this.calculatePasscodes(accountList);
          }
        });
  }

  private List<AccountPasscode> calculatePasscodes(List<Account> accountList) {
    List<AccountPasscode> accountPasscodeList = new ArrayList<>(accountList.size());
    for (Account account : accountList) {
      AccountPasscode accountPasscode = calculatePasscode(account);
      accountPasscodeList.add(accountPasscode);
    }
    return accountPasscodeList;
  }

  private AccountPasscode calculatePasscode(Account account) {
    // TODO: Pick right delta offset time
    OneTimePassword oneTimePassword = oneTimePasswordFactory.createOneTimePassword(account, 0);
    Passcode passcode = oneTimePassword.generate();
    return new AccountPasscode(account, account.getIssuer(), passcode);
  }
}
