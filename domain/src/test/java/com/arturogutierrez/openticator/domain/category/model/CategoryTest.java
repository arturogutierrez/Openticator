package com.arturogutierrez.openticator.domain.category.model;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CategoryTest {

  @Test
  public void testCategoryModelFields() {
    Category category = new Category("id", "name");

    assertThat(category.getCategoryId(), is("id"));
    assertThat(category.getName(), is("name"));
  }
}
