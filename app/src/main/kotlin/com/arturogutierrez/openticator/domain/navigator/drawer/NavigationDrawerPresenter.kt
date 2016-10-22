package com.arturogutierrez.openticator.domain.navigator.drawer

import com.arturogutierrez.openticator.domain.category.CategorySelector
import com.arturogutierrez.openticator.domain.category.interactor.GetCategoriesInteractor
import com.arturogutierrez.openticator.domain.category.model.Category
import com.arturogutierrez.openticator.interactor.DefaultSubscriber
import com.arturogutierrez.openticator.view.presenter.Presenter
import javax.inject.Inject

class NavigationDrawerPresenter @Inject constructor(
    val getCategoriesInteractor: GetCategoriesInteractor,
    val categorySelector: CategorySelector) : Presenter<NavigationDrawerView> {

  lateinit override var view: NavigationDrawerView

  // TODO: Presenter shouldn't has state
  private var categories: List<Category> = emptyList()

  override fun resume() {
    getCategoriesInteractor.execute(object : DefaultSubscriber<List<Category>>() {
      override fun onNext(item: List<Category>) {
        onFetchCategories(item)
      }
    })
  }

  override fun pause() {
    getCategoriesInteractor.unsubscribe()
  }

  override fun destroy() {
    getCategoriesInteractor.unsubscribe()
  }

  private fun onFetchCategories(categories: List<Category>) {
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
