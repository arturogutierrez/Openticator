package com.arturogutierrez.openticator.domain.navigator.drawer;

import com.arturogutierrez.openticator.domain.category.model.Category;
import java.util.List;

public interface NavigationDrawerView {

  void renderCategories(List<Category> categories);

  void dismissDrawer();
}
