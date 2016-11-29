package com.arturogutierrez.openticator.mapper

abstract class Mapper<FROM, TO> {

  abstract fun transform(value: FROM): TO

  abstract fun reverseTransform(value: TO): FROM

  fun transform(values: Collection<FROM>): List<TO> {
    return values.map { transform(it) }
  }

  fun reverseTransform(values: Collection<TO>): List<FROM> {
    return values.map { reverseTransform(it) }
  }
}
