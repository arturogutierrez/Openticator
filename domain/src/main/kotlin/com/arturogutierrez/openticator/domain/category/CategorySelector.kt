package com.arturogutierrez.openticator.domain.category

import com.arturogutierrez.openticator.domain.category.model.Category
import rx.Observable
import rx.subjects.PublishSubject

class CategorySelector {

  private val publishSubject = PublishSubject.create<Category>()
  private var currentCategory = Category.empty

  val selectedCategory: Observable<Category>
    get() = publishSubject.startWith(currentCategory)

  fun setSelectedCategory(category: Category) {
    currentCategory = category
    publishSubject.onNext(category)
  }

  fun removeSelectedCategory() {
    setSelectedCategory(Category.empty)
  }
}
