package com.arturogutierrez.openticator.view.fragment

import android.support.annotation.StringRes
import android.support.design.widget.BaseTransientBottomBar.Duration
import android.support.design.widget.Snackbar
import com.arturogutierrez.openticator.view.activity.BaseActivity

fun BaseFragment.makeSnackbar(@StringRes resId: Int, @Duration duration: Int) {
  val coordinatorLayout = (activity as BaseActivity).coordinatorLayout
  Snackbar.make(coordinatorLayout, resId, duration).show()
}
