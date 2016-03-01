package com.arturogutierrez.openticator.storage.realm.mapper;

import com.arturogutierrez.openticator.domain.category.model.Category;
import com.arturogutierrez.openticator.mapper.Mapper;
import com.arturogutierrez.openticator.storage.realm.model.CategoryRealm;
import javax.inject.Inject;

public class CategoryRealmMapper extends Mapper<Category, CategoryRealm> {

  @Inject
  public CategoryRealmMapper() {

  }

  @Override
  public CategoryRealm transform(Category category) {
    CategoryRealm categoryRealm = null;
    if (category != null) {
      categoryRealm = new CategoryRealm();
      categoryRealm.setCategoryId(category.getCategoryId());
      categoryRealm.setName(category.getName());
    }
    return categoryRealm;
  }

  @Override
  public Category reverseTransform(CategoryRealm categoryRealm) {
    Category category = null;
    if (categoryRealm != null) {
      category = new Category(categoryRealm.getCategoryId(), categoryRealm.getName());
    }
    return category;
  }
}
