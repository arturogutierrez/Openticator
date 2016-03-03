package com.arturogutierrez.openticator.domain.category;

import com.arturogutierrez.openticator.domain.category.model.Category;
import com.arturogutierrez.openticator.domain.category.model.EmptyCategory;
import java.util.UUID;
import javax.inject.Inject;

public class CategoryFactory {

  @Inject
  public CategoryFactory() {

  }

  public Category createCategory(String categoryName) {
    String categoryId = UUID.randomUUID().toString();
    return new Category(categoryId, categoryName);
  }

  public Category createEmptyCategory() {
    return new EmptyCategory();
  }
}
