package com.arturogutierrez.openticator.helpers

inline fun consume(f: () -> Unit): Boolean {
  f()
  return true
}
