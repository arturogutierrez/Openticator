package com.arturogutierrez.openticator.view.activity

import android.support.annotation.StringRes
import android.support.design.widget.BaseTransientBottomBar.Duration
import android.support.design.widget.Snackbar

fun BaseActivity.makeSnackbar(@StringRes resId: Int, @Duration duration: Int) {
  val coordinatorLayout = coordinatorLayout
  Snackbar.make(coordinatorLayout, resId, duration).show()
}
