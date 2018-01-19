package com.arturogutierrez.openticator.helpers

import android.app.Activity
import android.content.Context
import android.content.Intent

inline fun <reified T : Activity> Context.startActivity() {
  val intent = Intent(this, T::class.java)
  startActivity(intent)
}
