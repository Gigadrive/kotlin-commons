package com.gigadrivegroup.kotlincommons.ext

import java.text.DecimalFormat
import kotlin.math.abs

public fun Int.toBoolean(): Boolean {
    return this != 0
}

public fun Int.difference(other: Int): Int {
    return abs(this - other)
}

public fun Int.betweenExclusive(min: Int, max: Int): Boolean {
    return this in (min + 1) until max
}

public fun Int.formatNumber(): String {
    val pattern = "###,###.###"
    val decimalFormat = DecimalFormat(pattern)

    return decimalFormat.format(this)
}
