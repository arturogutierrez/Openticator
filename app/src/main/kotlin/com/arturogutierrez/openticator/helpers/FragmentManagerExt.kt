package com.arturogutierrez.openticator.helpers

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction

fun FragmentManager.executeTransaction(operations: FragmentTransaction.() -> Unit) {
  beginTransaction().apply {
    operations()
  }.commit()
}
