package com.arturogutierrez.openticator.domain.account.list;

import com.arturogutierrez.openticator.domain.account.interactor.DeleteAccountsInteractor;
import com.arturogutierrez.openticator.domain.account.interactor.UpdateAccountInteractor;
import com.arturogutierrez.openticator.domain.account.model.Account;
import com.arturogutierrez.openticator.domain.category.interactor.AddAccountToCategoryInteractor;
import com.arturogutierrez.openticator.domain.category.interactor.AddCategoryInteractor;
import com.arturogutierrez.openticator.domain.category.interactor.GetCategoriesInteractor;
import com.arturogutierrez.openticator.domain.category.model.Category;
import com.arturogutierrez.openticator.interactor.DefaultSubscriber;
import com.arturogutierrez.openticator.view.presenter.Presenter;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;

public class AccountEditModePresenter extends DefaultSubscriber<Void> implements Presenter {

  private final DeleteAccountsInteractor deleteAccountsInteractor;
  private final UpdateAccountInteractor updateAccountInteractor;
  private final GetCategoriesInteractor getCategoriesInteractor;
  private final AddCategoryInteractor addCategoryInteractor;
  private final AddAccountToCategoryInteractor addAccountToCategoryInteractor;
  private AccountEditModeView view;

  @Inject
  public AccountEditModePresenter(UpdateAccountInteractor updateAccountInteractor,
      DeleteAccountsInteractor deleteAccountsInteractor,
      GetCategoriesInteractor getCategoriesInteractor, AddCategoryInteractor addCategoryInteractor,
      AddAccountToCategoryInteractor addAccountToCategoryInteractor) {
    this.updateAccountInteractor = updateAccountInteractor;
    this.deleteAccountsInteractor = deleteAccountsInteractor;
    this.getCategoriesInteractor = getCategoriesInteractor;
    this.addCategoryInteractor = addCategoryInteractor;
    this.addAccountToCategoryInteractor = addAccountToCategoryInteractor;
  }

  public void setView(AccountEditModeView view) {
    this.view = view;
  }

  @Override
  public void resume() {

  }

  @Override
  public void pause() {

  }

  @Override
  public void destroy() {
    deleteAccountsInteractor.unsubscribe();
  }

  public void deleteAccounts(Set<Account> selectedAccounts) {
    deleteAccountsInteractor.configure(selectedAccounts);
    deleteAccountsInteractor.execute(new DeleteAccountsSubscriber());
  }

  public void onSelectedAccounts(Set<Account> selectedAccounts) {
    if (selectedAccounts.size() == 0) {
      view.dismissActionMode();
      return;
    }

    view.showCategoryButton(selectedAccounts.size() == 1);
    view.showEditButton(selectedAccounts.size() == 1);
  }

  public void updateAccount(Account account, String newName) {
    if (newName.length() > 0) {
      updateAccountInteractor.configure(account, newName);
      updateAccountInteractor.execute(new UpdateAccountSubscriber());
    }

    view.dismissActionMode();
  }

  public void pickCategoryForAccount(Account account) {
    getCategoriesInteractor.execute(new GetCategoriesSubscriber(account));
  }

  public void addAccountToCategory(Category category, Account account) {
    addAccountToCategoryInteractor.configure(category, account);
    addAccountToCategoryInteractor.execute(new AddAccountToCategorySubscriber());

    view.dismissActionMode();
  }

  public void createCategory(String categoryName, Account account) {
    addCategoryInteractor.configure(categoryName, account);
    addCategoryInteractor.execute(new CreateCategorySubscriber());

    view.dismissActionMode();
  }

  private class UpdateAccountSubscriber extends DefaultSubscriber<Account> {
    @Override
    public void onNext(Account account) {
      view.dismissActionMode();
    }

    @Override
    public void onError(Throwable e) {
      view.dismissActionMode();
    }
  }

  private class DeleteAccountsSubscriber extends DefaultSubscriber<Void> {
    @Override
    public void onNext(Void aVoid) {
      view.dismissActionMode();
    }

    @Override
    public void onError(Throwable e) {
      view.dismissActionMode();
    }
  }

  private class GetCategoriesSubscriber extends DefaultSubscriber<List<Category>> {
    private final Account account;

    private GetCategoriesSubscriber(Account account) {
      this.account = account;
    }

    @Override
    public void onNext(List<Category> categories) {
      getCategoriesInteractor.unsubscribe();

      if (categories.size() == 0) {
        view.showChooseEmptyCategory(account);
      } else {
        view.showChooseCategory(categories, account);
      }
    }

    @Override
    public void onError(Throwable e) {
      view.dismissActionMode();
    }
  }

  private class CreateCategorySubscriber extends DefaultSubscriber<Category> {

    @Override
    public void onNext(Category category) {
      view.dismissActionMode();
    }

    @Override
    public void onError(Throwable e) {
      view.dismissActionMode();
    }
  }

  private class AddAccountToCategorySubscriber extends DefaultSubscriber<Category> {

    @Override
    public void onNext(Category category) {
      view.dismissActionMode();
    }

    @Override
    public void onError(Throwable e) {
      view.dismissActionMode();
    }
  }
}
