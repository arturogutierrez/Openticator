package com.arturogutierrez.openticator.storage.export.mapper;

import com.arturogutierrez.openticator.ApplicationTestCase;
import com.arturogutierrez.openticator.domain.category.model.Category;
import com.arturogutierrez.openticator.storage.export.model.CategoryEntity;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class CategoryEntityMapperTest extends ApplicationTestCase {

  private static final String FAKE_ID = "id";
  private static final String FAKE_NAME = "name";

  private CategoryEntityMapper categoryEntityMapper;

  @Before
  public void setUp() {
    categoryEntityMapper = new CategoryEntityMapper();
  }

  @Test
  public void testNullCategoryToCategoryEntity() {
    CategoryEntity categoryEntity = categoryEntityMapper.transform((Category) null);

    assertThat(categoryEntity, is(nullValue()));
  }

  @Test
  public void testCategoryToCategoryEntity() {
    Category category = new Category(FAKE_ID, FAKE_NAME);

    CategoryEntity categoryEntity = categoryEntityMapper.transform(category);

    assertThat(categoryEntity.getCategoryId(), is(FAKE_ID));
    assertThat(categoryEntity.getName(), is(FAKE_NAME));
  }

  @Test
  public void testCategoryEntityToCategory() {
    CategoryEntity categoryEntity = new CategoryEntity(FAKE_ID, FAKE_NAME);

    Category category = categoryEntityMapper.reverseTransform(categoryEntity);

    assertThat(category.getCategoryId(), is(FAKE_ID));
    assertThat(category.getName(), is(FAKE_NAME));
  }
}
