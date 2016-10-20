package com.arturogutierrez.openticator.domain.navigator.drawer

import com.arturogutierrez.openticator.domain.category.model.Category

interface NavigationDrawerView {

  fun renderCategories(categories: List<Category>)

  fun dismissDrawer()
}
