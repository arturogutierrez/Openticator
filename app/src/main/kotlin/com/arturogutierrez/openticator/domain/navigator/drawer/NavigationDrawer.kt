package com.arturogutierrez.openticator.domain.navigator.drawer

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.arturogutierrez.openticator.R
import com.arturogutierrez.openticator.domain.category.model.Category
import com.arturogutierrez.openticator.domain.navigator.Navigator
import com.arturogutierrez.openticator.helpers.find
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.SectionDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import javax.inject.Inject

class NavigationDrawer @Inject constructor(val activity: Activity,
    val presenter: NavigationDrawerPresenter,
    val navigator: Navigator) : NavigationDrawerView {

  private val toolbar by lazy { activity.find<Toolbar>(R.id.toolbar) }
  private val allAccountsDrawerItem = createAllAccountsDrawerItem()
  private val categoriesSectionDrawerItem = createCategoriesSectionDrawerItem()
  private val settingsDrawerItem = createSettingsDrawerItem()

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
        .addStickyDrawerItems(settingsDrawerItem)
        .withOnDrawerItemClickListener { _, position, drawerItem -> onDrawerItemClicked(drawerItem, position) }
        .withSelectedItemByPosition(1)
        .build()
  }

  override fun renderCategories(categories: List<Category>) {
    drawer.removeAllItems()
    drawer.addItems(categoriesSectionDrawerItem, allAccountsDrawerItem)

    categories.map { createCategoryDrawerItem(it) }
        .forEach { drawer.addItem(it) }
  }

  override fun dismissDrawer() {
    drawer.closeDrawer()
  }

  private fun createSettingsDrawerItem(): PrimaryDrawerItem {
    return PrimaryDrawerItem()
        .withName(R.string.settings)
        .withIcon(R.drawable.ic_settings_black_24dp)
        .withIconTintingEnabled(true)
  }

  private fun createAllAccountsDrawerItem(): PrimaryDrawerItem {
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
    when (drawerItem) {
      allAccountsDrawerItem -> presenter.allCategoriesItemClicked()
      settingsDrawerItem -> navigator.goToSettings(activity)
      else -> presenter.categoryItemClicked(position - 2)
    }
    return true
  }
}
