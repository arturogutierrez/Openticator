package com.arturogutierrez.openticator.domain.category;

import com.arturogutierrez.openticator.domain.category.model.Category;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class CategoryFactoryTest {

  private CategoryFactory categoryFactory;

  @Before
  public void setUp() {
    categoryFactory = new CategoryFactory();
  }

  @Test
  public void testCreatingCategory() {
    Category category = categoryFactory.createCategory("name");

    assertThat(category.getCategoryId(), is(notNullValue()));
    assertThat(category.getName(), is("name"));
  }
}
