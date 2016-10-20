package com.arturogutierrez.openticator.domain.navigator.drawer

import com.arturogutierrez.openticator.domain.category.CategorySelector
import com.arturogutierrez.openticator.domain.category.interactor.GetCategoriesInteractor
import com.arturogutierrez.openticator.domain.category.model.Category
import com.arturogutierrez.openticator.interactor.DefaultSubscriber
import com.arturogutierrez.openticator.view.presenter.Presenter
import javax.inject.Inject

@Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
class NavigationDrawerPresenter @Inject constructor(
    val getCategoriesInteractor: GetCategoriesInteractor,
    val categorySelector: CategorySelector) : DefaultSubscriber<List<Category>>(), Presenter {

  private lateinit var view: NavigationDrawerView
  // TODO: Presenter shouldn't has state
  private var categories: List<Category> = emptyList()

  fun setView(view: NavigationDrawerView) {
    this.view = view
  }

  override fun resume() {
    getCategoriesInteractor.execute(this)
  }

  override fun pause() {
    getCategoriesInteractor.unsubscribe()
  }

  override fun destroy() {
    getCategoriesInteractor.unsubscribe()
  }

  override fun onNext(categories: List<Category>) {
    this.categories = categories
    view.renderCategories(categories)
  }

  fun categoryItemClicked(position: Int) {
    if (position < categories.size) {
      categorySelector.setSelectedCategory(categories[position])
    }

    view.dismissDrawer()
  }

  fun allCategoriesItemClicked() {
    categorySelector.removeSelectedCategory()
    view.dismissDrawer()
  }
}
