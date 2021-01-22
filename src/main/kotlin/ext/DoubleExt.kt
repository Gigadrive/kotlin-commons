package com.gigadrivegroup.kotlincommons.ext

import java.text.DecimalFormat
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.roundToInt

public fun Double.difference(other: Double): Double {
    return abs(this - other)
}

public fun Double.formatNumber(): String {
    val pattern = "###,###.###"
    val decimalFormat = DecimalFormat(pattern)

    return decimalFormat.format(this)
}

public fun Double.round(places: Int): Double {
    val p = 10.0.pow(places.toDouble())
    return (this * p).roundToInt() / p
}
