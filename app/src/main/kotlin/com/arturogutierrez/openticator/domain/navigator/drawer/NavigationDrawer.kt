package com.arturogutierrez.openticator.domain.navigator.drawer

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.arturogutierrez.openticator.R
import com.arturogutierrez.openticator.domain.category.model.Category
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.SectionDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import org.jetbrains.anko.find
import javax.inject.Inject

class NavigationDrawer @Inject constructor(val activity: Activity, val presenter: NavigationDrawerPresenter) : NavigationDrawerView {

  private lateinit var toolbar: Toolbar
  private lateinit var drawer: Drawer
  private lateinit var categoriesSectionDrawerItem: SectionDrawerItem
  private lateinit var allAccountsDrawerItem: PrimaryDrawerItem

  init {
    initialize(activity)
  }

  fun onCreate(savedInstanceState: Bundle?) {
    createDrawerLayout(savedInstanceState)
  }

  fun onResume() {
    presenter.resume()
  }

  fun onPause() {
    presenter.pause()
  }

  fun onDestroy() {
    presenter.destroy()
  }

  fun onBackPressed(): Boolean {
    if (drawer.isDrawerOpen) {
      drawer.closeDrawer()
      return true
    }

    return false
  }

  private fun initialize(activity: Activity) {
    toolbar = activity.find(R.id.toolbar)
    presenter.setView(this)
  }

  private fun createDrawerLayout(savedInstanceState: Bundle?) {
    allAccountsDrawerItem = PrimaryDrawerItem().withName(R.string.all_accounts).withIcon(R.drawable.ic_folder_black_24dp).withIconTintingEnabled(true)
    categoriesSectionDrawerItem = SectionDrawerItem().withName(R.string.categories).withDivider(false)

    drawer = DrawerBuilder().withSavedInstance(savedInstanceState).withActivity(activity).withToolbar(toolbar).withActionBarDrawerToggleAnimated(true).addDrawerItems(categoriesSectionDrawerItem, allAccountsDrawerItem).withOnDrawerItemClickListener { view, position, drawerItem -> onDrawerItemClicked(drawerItem, position) }.withSelectedItemByPosition(1).build()
  }

  override fun renderCategories(categories: List<Category>) {
    drawer.removeAllItems()
    drawer.addItems(categoriesSectionDrawerItem, allAccountsDrawerItem)

    for (category in categories) {
      val drawerItem = createCategoryDrawerItem(category)
      drawer.addItem(drawerItem)
    }
  }

  override fun dismissDrawer() {
    drawer.closeDrawer()
  }

  private fun createCategoryDrawerItem(category: Category): PrimaryDrawerItem {
    return PrimaryDrawerItem().withName(category.name).withLevel(5)
  }

  private fun onDrawerItemClicked(drawerItem: IDrawerItem<*, *>, position: Int): Boolean {
    if (drawerItem === allAccountsDrawerItem) {
      presenter.allCategoriesItemClicked()
    } else {
      presenter.categoryItemClicked(position - 2)
    }
    return true
  }
}
