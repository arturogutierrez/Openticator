package com.arturogutierrez.openticator.domain.navigator.drawer;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.arturogutierrez.openticator.R;
import com.arturogutierrez.openticator.domain.category.model.Category;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import java.util.List;
import javax.inject.Inject;

public class NavigationDrawer implements NavigationDrawerView {

  @Bind(R.id.toolbar)
  Toolbar toolbar;

  private final Activity activity;
  private final NavigationDrawerPresenter presenter;

  private Drawer drawer;
  private SectionDrawerItem categoriesSectionDrawerItem;
  private PrimaryDrawerItem allAccountsDrawerItem;

  @Inject
  public NavigationDrawer(Activity activity, NavigationDrawerPresenter presenter) {
    this.activity = activity;
    this.presenter = presenter;

    initialize(activity);
  }

  public void onCreate(Bundle savedInstanceState) {
    createDrawerLayout(savedInstanceState);
  }

  public void onResume() {
    presenter.resume();
  }

  public void onPause() {
    presenter.pause();
  }

  public void onDestroy() {
    presenter.destroy();
  }

  public boolean onBackPressed() {
    if (drawer != null && drawer.isDrawerOpen()) {
      drawer.closeDrawer();
      return true;
    }

    return false;
  }

  private void initialize(Activity activity) {
    ButterKnife.bind(this, activity);
    presenter.setView(this);
  }

  private void createDrawerLayout(Bundle savedInstanceState) {
    allAccountsDrawerItem = new PrimaryDrawerItem().withName("All accounts")
        .withIcon(R.drawable.ic_folder_black_24dp)
        .withIconTintingEnabled(true);
    categoriesSectionDrawerItem = new SectionDrawerItem().withName("Categories").withDivider(false);

    drawer = new DrawerBuilder().withSavedInstance(savedInstanceState)
        .withActivity(activity)
        .withToolbar(toolbar)
        .withActionBarDrawerToggleAnimated(true)
        .addDrawerItems(categoriesSectionDrawerItem, allAccountsDrawerItem)
        .withOnDrawerItemClickListener(
            (view, position, drawerItem) -> onDrawerItemClicked(drawerItem, position))
        .withSelectedItemByPosition(1)
        .build();
  }

  @Override
  public void renderCategories(List<Category> categories) {
    drawer.removeAllItems();
    drawer.addItems(categoriesSectionDrawerItem, allAccountsDrawerItem);

    for (Category category : categories) {
      PrimaryDrawerItem drawerItem = createCategoryDrawerItem(category);
      drawer.addItem(drawerItem);
    }
  }

  @Override
  public void dismissDrawer() {
    drawer.closeDrawer();
  }

  private PrimaryDrawerItem createCategoryDrawerItem(Category category) {
    return new PrimaryDrawerItem().withName(category.getName()).withLevel(5);
  }

  private boolean onDrawerItemClicked(IDrawerItem drawerItem, int position) {
    if (drawerItem == allAccountsDrawerItem) {
      presenter.allCategoriesItemClicked();
    } else {
      presenter.categoryItemClicked(position - 2);
    }
    return true;
  }
}
