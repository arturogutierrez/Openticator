package com.arturogutierrez.openticator.view.activity

import android.support.annotation.StringRes
import android.support.design.widget.Snackbar

fun BaseActivity.makeSnackbar(@StringRes resId: Int, @Snackbar.Duration duration: Int) {
  val coordinatorLayout = coordinatorLayout
  Snackbar.make(coordinatorLayout, resId, duration).show()
}
