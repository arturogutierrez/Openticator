package com.arturogutierrez.openticator.mapper

import java.util.*

abstract class Mapper<FROM, TO> {

    abstract fun transform(value: FROM): TO

    abstract fun reverseTransform(value: TO): FROM

    fun transform(values: Collection<FROM>): List<TO> {
        val transformedValues = ArrayList<TO>(values.size)
        for (value in values) {
            transformedValues.add(transform(value))
        }
        return transformedValues
    }

    fun reverseTransform(values: Collection<TO>): List<FROM> {
        val transformedValues = ArrayList<FROM>(values.size)
        for (value in values) {
            transformedValues.add(reverseTransform(value))
        }
        return transformedValues
    }
}
