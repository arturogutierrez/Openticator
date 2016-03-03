package com.arturogutierrez.openticator.domain.account.interactor;

import com.arturogutierrez.openticator.domain.account.AccountFactory;
import com.arturogutierrez.openticator.domain.account.model.Account;
import com.arturogutierrez.openticator.domain.account.repository.AccountRepository;
import com.arturogutierrez.openticator.domain.category.CategoryFactory;
import com.arturogutierrez.openticator.domain.category.CategorySelector;
import com.arturogutierrez.openticator.domain.category.model.Category;
import com.arturogutierrez.openticator.domain.category.repository.CategoryRepository;
import com.arturogutierrez.openticator.executor.PostExecutionThread;
import com.arturogutierrez.openticator.executor.ThreadExecutor;
import com.arturogutierrez.openticator.interactor.Interactor;
import rx.Observable;

public class AddAccountInteractor extends Interactor<Account> {

  private final AccountRepository accountRepository;
  private final AccountFactory accountFactory;
  private final CategoryRepository categoryRepository;
  private final CategorySelector categorySelector;
  private final CategoryFactory categoryFactory;

  private Account newAccount;

  public AddAccountInteractor(AccountRepository accountRepository, AccountFactory accountFactory,
      CategoryRepository categoryRepository, CategorySelector categorySelector,
      CategoryFactory categoryFactory, ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.accountRepository = accountRepository;
    this.accountFactory = accountFactory;
    this.categoryRepository = categoryRepository;
    this.categorySelector = categorySelector;
    this.categoryFactory = categoryFactory;
  }

  public void configure(String accountName, String accountSecret) {
    newAccount = accountFactory.createAccount(accountName, accountSecret);
  }

  @Override
  public Observable<Account> createObservable() {
    if (newAccount == null) {
      throw new IllegalStateException("You must call configure before execute the interactor");
    }

    return categorySelector.getSelectedCategory().flatMap(category -> {
      Category emptyCategory = categoryFactory.createEmptyCategory();
      if (category.equals(emptyCategory)) {
        return accountRepository.add(newAccount);
      }

      return accountRepository.add(newAccount)
          .flatMap(createdAccount -> categoryRepository.addAccount(category, createdAccount)
              .flatMap(categoryWhereAccountJustAdded -> Observable.just(createdAccount)));
    });
  }
}
