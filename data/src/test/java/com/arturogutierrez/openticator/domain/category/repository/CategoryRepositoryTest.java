package com.arturogutierrez.openticator.domain.category.repository;

import com.arturogutierrez.openticator.ApplicationTestCase;
import com.arturogutierrez.openticator.domain.category.model.Category;
import com.arturogutierrez.openticator.storage.CategoryDiskDataStore;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

public class CategoryRepositoryTest extends ApplicationTestCase {

  private CategoryRepository categoryRepository;
  @Mock
  private CategoryDiskDataStore mockCategoryDiskDataStore;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);

    categoryRepository = new CategoryRepositoryImpl(mockCategoryDiskDataStore);
  }

  @Test
  public void testAddCategory() {
    Category category = new Category("id", "name");

    categoryRepository.add(category);

    verify(mockCategoryDiskDataStore).add(category);
  }

  @Test
  public void testGetCategories() {
    categoryRepository.getCategories();

    verify(mockCategoryDiskDataStore).getCategories();
  }
}
