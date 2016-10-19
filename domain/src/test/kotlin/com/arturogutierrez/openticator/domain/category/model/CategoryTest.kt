package com.arturogutierrez.openticator.domain.category.model

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class CategoryTest {

    @Test
    fun testCategoryModelFields() {
        val category = Category("id", "name")

        assertThat(category.categoryId, `is`("id"))
        assertThat(category.name, `is`("name"))
    }
}
