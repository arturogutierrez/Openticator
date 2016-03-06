package com.arturogutierrez.openticator.storage.export.model;

public class CategoryEntity {

  private final String categoryId;
  private final String name;

  public CategoryEntity(String categoryId, String name) {
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
