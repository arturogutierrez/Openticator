package com.arturogutierrez.openticator.mapper

interface Mapper<FROM, TO> {

  fun transform(value: FROM): TO

  fun reverseTransform(value: TO): FROM

  fun transform(values: Collection<FROM>) = values.map { transform(it) }

  fun reverseTransform(values: Collection<TO>) = values.map { reverseTransform(it) }
}
