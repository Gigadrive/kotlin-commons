package com.gigadrivegroup.kotlincommons.feature

import com.gigadrivegroup.kotlincommons.ext.difference
import com.gigadrivegroup.kotlincommons.util.ALPHANUMERIC_CHARACTERS
import java.util.Random

public fun join(glue: String, elements: Array<out String>): String {
    val builder = StringBuilder()

    elements.forEach {
        if (builder.isNotEmpty()) {
            builder.append(glue)
        }

        builder.append(it)
    }

    return builder.toString()
}

public fun randomString(length: Int): String {
    return randomString(length, length)
}

public fun randomString(minimum: Int, maximum: Int): String {
    var minimum = minimum
    var maximum = maximum
    if (minimum > maximum) {
        val a = minimum
        maximum = minimum
        minimum = a
    }
    val chars = ALPHANUMERIC_CHARACTERS
    val sb = java.lang.StringBuilder()
    val random = Random()
    while (sb.length < maximum) {
        val index = (random.nextFloat() * chars.length).toInt()
        sb.append(chars[index])
    }
    var s = sb.toString()
    if (minimum != maximum) {
        val difference: Int = minimum.difference(maximum)
        if (difference > 0) {
            s = s.substring(0, s.length - randomInteger(0, difference))
        }
    }
    return s
}

public fun wrapLore(text: String): List<String> {
    val sb: java.lang.StringBuilder = java.lang.StringBuilder(text)

    var i = 0
    while (i + 35 < sb.length && sb.lastIndexOf(" ", i + 35).also { i = it } != -1) {
        sb.replace(i, i + 1, "\n")
    }

    return sb.toString().split("\n").toTypedArray().toList()
}
