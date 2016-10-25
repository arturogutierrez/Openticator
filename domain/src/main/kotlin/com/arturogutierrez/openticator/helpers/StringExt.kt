package com.arturogutierrez.openticator.helpers

import java.util.regex.Pattern

fun String.isBase32(): Boolean {
  val pattern = Pattern.compile("[a-z2-7]+")
  return pattern.matcher(toUpperCase()).matches()
}
