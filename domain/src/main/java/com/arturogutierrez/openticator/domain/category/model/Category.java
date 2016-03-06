package com.arturogutierrez.openticator.domain.category.model;

public class Category {

  private final String categoryId;
  private final String name;

  public Category(String categoryId, String name) {
    this.categoryId = categoryId;
    this.name = name;
  }

  public String getCategoryId() {
    return categoryId;
  }

  public String getName() {
    return name;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Category)) {
      return false;
    }
    if (obj == this) {
      return true;
    }

    return categoryId.equals(((Category) obj).getCategoryId());
  }

  @Override
  public int hashCode() {
    return categoryId.hashCode();
  }

  public static Category emptyCategory() {
    return new EmptyCategory();
  }
}
