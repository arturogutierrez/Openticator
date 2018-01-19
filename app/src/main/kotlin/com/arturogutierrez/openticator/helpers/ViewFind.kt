package com.arturogutierrez.openticator.helpers

import android.app.Activity
import android.support.v4.app.Fragment
import android.view.View

inline fun <reified T : View> Activity.find(id: Int): T = findViewById(id)

inline fun <reified T : View> Activity.findOptional(id: Int): T? = findViewById(id)

inline fun <reified T : View> Fragment.find(id: Int): T = view?.findViewById(id) as T

inline fun <reified T : View> View.find(id: Int): T = findViewById(id)
