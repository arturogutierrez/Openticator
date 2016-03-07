package com.arturogutierrez.openticator.storage.export.mapper;

import com.arturogutierrez.openticator.domain.category.model.Category;
import com.arturogutierrez.openticator.mapper.Mapper;
import com.arturogutierrez.openticator.storage.export.model.CategoryEntity;
import javax.inject.Inject;

public class CategoryEntityMapper extends Mapper<Category, CategoryEntity> {

  @Inject
  public CategoryEntityMapper() {

  }

  @Override
  public CategoryEntity transform(Category category) {
    CategoryEntity categoryEntity = null;
    if (category != null) {
      categoryEntity = new CategoryEntity(category.getCategoryId(), category.getName());
    }
    return categoryEntity;
  }

  @Override
  public Category reverseTransform(CategoryEntity categoryEntity) {
    Category category = null;
    if (categoryEntity != null) {
      category = new Category(categoryEntity.getCategoryId(), categoryEntity.getName());
    }
    return category;
  }
}
