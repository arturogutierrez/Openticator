package com.arturogutierrez.openticator.domain.category;

import com.arturogutierrez.openticator.domain.category.model.Category;
import com.arturogutierrez.openticator.domain.category.model.EmptyCategory;
import rx.Observable;
import rx.subjects.PublishSubject;

public class CategorySelector {

  private final PublishSubject<Category> publishSubject;
  private Category currentCategory;

  public CategorySelector() {
    this.currentCategory = new EmptyCategory();
    this.publishSubject = PublishSubject.create();
  }

  public Observable<Category> getSelectedCategory() {
    return publishSubject.startWith(currentCategory);
  }

  public void setSelectedCategory(Category category) {
    currentCategory = category;
    publishSubject.onNext(category);
  }

  public void removeSelectedCategory() {
    setSelectedCategory(new EmptyCategory());
  }
}
