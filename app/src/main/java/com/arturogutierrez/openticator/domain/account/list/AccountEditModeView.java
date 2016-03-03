package com.arturogutierrez.openticator.domain.account.list;

import com.arturogutierrez.openticator.domain.account.model.Account;
import com.arturogutierrez.openticator.domain.category.model.Category;
import java.util.List;

public interface AccountEditModeView {

  void dismissActionMode();

  void showCategoryButton(boolean isVisible);

  void showEditButton(boolean isVisible);

  void showLogoButton(boolean isVisible);

  void showChooseEmptyCategory(Account account);

  void showChooseCategory(List<Category> categories, Account account);
}
