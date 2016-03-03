package com.arturogutierrez.openticator.storage.realm.mapper;

import com.arturogutierrez.openticator.ApplicationTestCase;
import com.arturogutierrez.openticator.domain.category.model.Category;
import com.arturogutierrez.openticator.storage.realm.model.CategoryRealm;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class CategoryRealmMapperTest extends ApplicationTestCase {

  private static final String FAKE_ID = "id";
  private static final String FAKE_NAME = "name";

  private CategoryRealmMapper categoryRealmMapper;

  @Before
  public void setUp() {
    categoryRealmMapper = new CategoryRealmMapper();
  }

  @Test
  public void testNullCategoryToRealm() {
    CategoryRealm categoryRealm = categoryRealmMapper.transform((Category) null);

    assertThat(categoryRealm, is(nullValue()));
  }

  @Test
  public void testCategoryToRealm() {
    Category category = new Category(FAKE_ID, FAKE_NAME);

    CategoryRealm categoryRealm = categoryRealmMapper.transform(category);

    assertThat(categoryRealm.getCategoryId(), is(FAKE_ID));
    assertThat(categoryRealm.getName(), is(FAKE_NAME));
  }

  @Test
  public void testCategoryRealmToCategory() {
    CategoryRealm categoryRealm = new CategoryRealm();
    categoryRealm.setCategoryId(FAKE_ID);
    categoryRealm.setName(FAKE_NAME);

    Category category = categoryRealmMapper.reverseTransform(categoryRealm);

    assertThat(category.getCategoryId(), is(FAKE_ID));
    assertThat(category.getName(), is(FAKE_NAME));
  }
}
