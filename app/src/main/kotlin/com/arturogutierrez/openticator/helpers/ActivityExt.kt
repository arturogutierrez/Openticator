package com.arturogutierrez.openticator.helpers

import android.app.Activity
import android.support.design.widget.AppBarLayout
import android.support.design.widget.AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
import android.support.design.widget.AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
import android.support.design.widget.CoordinatorLayout
import android.view.View
import com.arturogutierrez.openticator.R

fun Activity.disableAppBarLayoutScroll() {
  val toolbar = findViewById<View>(R.id.toolbar)
  val appBarLayout = findViewById<AppBarLayout>(R.id.appbar_layout)

  if (toolbar == null || appBarLayout == null) {
    return
  }

  toolbar.apply {
    val toolbarLayoutParams = toolbar.layoutParams as AppBarLayout.LayoutParams
    toolbarLayoutParams.scrollFlags = 0
    layoutParams = toolbarLayoutParams
  }

  appBarLayout.apply {
    val appBarLayoutParams = appBarLayout.layoutParams as CoordinatorLayout.LayoutParams
    appBarLayoutParams.behavior = null
    layoutParams = appBarLayoutParams
  }
}

fun Activity.enableAppBarLayoutScroll() {
  val toolbar = findViewById<View>(R.id.toolbar)
  val appBarLayout = findViewById<AppBarLayout>(R.id.appbar_layout)

  if (toolbar == null || appBarLayout == null) {
    return
  }

  toolbar.apply {
    val toolbarLayoutParams = toolbar.layoutParams as AppBarLayout.LayoutParams
    toolbarLayoutParams.scrollFlags = SCROLL_FLAG_SCROLL or SCROLL_FLAG_ENTER_ALWAYS
    layoutParams = toolbarLayoutParams
  }

  appBarLayout.apply {
    val appBarLayoutParams = appBarLayout.layoutParams as CoordinatorLayout.LayoutParams
    appBarLayoutParams.behavior = AppBarLayout.Behavior()
    layoutParams = appBarLayoutParams
  }
}
