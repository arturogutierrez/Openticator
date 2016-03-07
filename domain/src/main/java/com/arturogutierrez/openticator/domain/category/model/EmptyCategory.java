package com.arturogutierrez.openticator.domain.category.model;

public class EmptyCategory extends Category {

  private static final String EMPTY_CATEGORY_ID = "empty";
  private static final String EMPTY_CATEGORY_NAME = "emptyCategory";

  public EmptyCategory() {
    super(EMPTY_CATEGORY_ID, EMPTY_CATEGORY_NAME);
  }
}
