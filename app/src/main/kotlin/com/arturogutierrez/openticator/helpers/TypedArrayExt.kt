package com.arturogutierrez.openticator.helpers

import android.content.res.TypedArray

fun TypedArray.use(block: TypedArray.() -> Unit) {
  try {
    block()
  } finally {
    recycle()
  }
}
