package com.arturogutierrez.openticator.domain.category

import com.arturogutierrez.openticator.domain.category.model.Category
import io.reactivex.Flowable
import io.reactivex.processors.BehaviorProcessor
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategorySelector @Inject constructor() {

  private val publishSubject = BehaviorProcessor.create<Category>()
  private var currentCategory = Category.empty

  val selectedCategory: Flowable<Category>
    get() = publishSubject.startWith(currentCategory)

  fun setSelectedCategory(category: Category) {
    currentCategory = category
    publishSubject.onNext(category)
  }

  fun removeSelectedCategory() {
    setSelectedCategory(Category.empty)
  }
}
