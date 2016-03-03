package com.arturogutierrez.openticator.domain.navigator.drawer;

import com.arturogutierrez.openticator.domain.category.CategorySelector;
import com.arturogutierrez.openticator.domain.category.interactor.GetCategoriesInteractor;
import com.arturogutierrez.openticator.domain.category.model.Category;
import com.arturogutierrez.openticator.interactor.DefaultSubscriber;
import com.arturogutierrez.openticator.view.presenter.Presenter;
import java.util.List;
import javax.inject.Inject;

public class NavigationDrawerPresenter extends DefaultSubscriber<List<Category>>
    implements Presenter {

  private final GetCategoriesInteractor getCategoriesInteractor;
  private final CategorySelector categorySelector;

  private NavigationDrawerView view;
  private List<Category> categories;

  @Inject
  public NavigationDrawerPresenter(GetCategoriesInteractor getCategoriesInteractor,
      CategorySelector categorySelector) {
    this.getCategoriesInteractor = getCategoriesInteractor;
    this.categorySelector = categorySelector;
  }

  public void setView(NavigationDrawerView view) {
    this.view = view;
  }

  @Override
  public void resume() {
    getCategoriesInteractor.execute(this);
  }

  @Override
  public void pause() {
    getCategoriesInteractor.unsubscribe();
  }

  @Override
  public void destroy() {
    getCategoriesInteractor.unsubscribe();
  }

  @Override
  public void onNext(List<Category> categories) {
    this.categories = categories;
    view.renderCategories(categories);
  }

  public void categoryItemClicked(int position) {
    if (position < categories.size()) {
      categorySelector.setSelectedCategory(categories.get(position));
    }

    view.dismissDrawer();
  }

  public void allCategoriesItemClicked() {
    categorySelector.removeSelectedCategory();
    view.dismissDrawer();
  }
}
