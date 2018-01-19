package com.arturogutierrez.openticator.domain.navigator.drawer

import com.arturogutierrez.openticator.domain.category.CategorySelector
import com.arturogutierrez.openticator.domain.category.interactor.GetCategoriesInteractor
import com.arturogutierrez.openticator.domain.category.model.Category
import com.arturogutierrez.openticator.interactor.DefaultFlowableObserver
import com.arturogutierrez.openticator.view.presenter.Presenter
import javax.inject.Inject

class NavigationDrawerPresenter @Inject constructor(
    private val getCategoriesInteractor: GetCategoriesInteractor,
    private val categorySelector: CategorySelector)
  : Presenter<NavigationDrawerView>() {

  // TODO: Presenter shouldn't has state
  private var categories: List<Category> = emptyList()

  override fun resume() {
    val params = GetCategoriesInteractor.EmptyParams
    getCategoriesInteractor.execute(params, object : DefaultFlowableObserver<List<Category>>() {
      override fun onNext(t: List<Category>) {
        onFetchCategories(t)
      }
    })
  }

  override fun pause() {
    getCategoriesInteractor.dispose()
  }

  override fun destroy() {
    getCategoriesInteractor.dispose()
  }

  private fun onFetchCategories(categories: List<Category>) {
    this.categories = categories
    view?.renderCategories(categories)
  }

  fun categoryItemClicked(position: Int) {
    if (position < categories.size) {
      categorySelector.setSelectedCategory(categories[position])
    }

    view?.dismissDrawer()
  }

  fun allCategoriesItemClicked() {
    categorySelector.removeSelectedCategory()
    view?.dismissDrawer()
  }
}
