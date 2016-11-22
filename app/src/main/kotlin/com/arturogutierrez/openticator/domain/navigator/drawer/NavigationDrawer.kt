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

  private val toolbar by lazy { activity.find<Toolbar>(R.id.toolbar) }
  private val allAccountsDrawerItem by lazy { createAllAcccountsDrawerItem() }
  private val categoriesSectionDrawerItem by lazy { createCategoriesSectionDrawerItem() }
  private lateinit var drawer: Drawer

  fun onCreate(savedInstanceState: Bundle?) {
    createDrawerLayout(savedInstanceState)
  }

  fun onResume() {
    presenter.attachView(this)
    presenter.resume()
  }

  fun onPause() {
    presenter.detachView()
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

  private fun createDrawerLayout(savedInstanceState: Bundle?) {
    drawer = DrawerBuilder()
        .withSavedInstance(savedInstanceState)
        .withActivity(activity)
        .withToolbar(toolbar)
        .withActionBarDrawerToggleAnimated(true)
        .addDrawerItems(categoriesSectionDrawerItem, allAccountsDrawerItem)
        .withOnDrawerItemClickListener { view, position, drawerItem -> onDrawerItemClicked(drawerItem, position) }
        .withSelectedItemByPosition(1)
        .build()
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

  private fun createAllAcccountsDrawerItem(): PrimaryDrawerItem {
    return PrimaryDrawerItem()
        .withName(R.string.all_accounts)
        .withIcon(R.drawable.ic_folder_black_24dp)
        .withIconTintingEnabled(true)
  }

  private fun createCategoriesSectionDrawerItem(): SectionDrawerItem {
    return SectionDrawerItem()
        .withName(R.string.categories)
        .withDivider(false)
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
