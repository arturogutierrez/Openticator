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
}
