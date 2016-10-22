package com.arturogutierrez.openticator.domain.navigator.drawer

import com.arturogutierrez.openticator.domain.category.model.Category
import com.arturogutierrez.openticator.view.presenter.Presenter

interface NavigationDrawerView : Presenter.View {

  fun renderCategories(categories: List<Category>)

  fun dismissDrawer()
}
