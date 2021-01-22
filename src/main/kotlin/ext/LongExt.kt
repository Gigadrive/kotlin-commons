package com.gigadrivegroup.kotlincommons.ext

import java.text.DecimalFormat

public fun Long.formatNumber(): String {
    val pattern = "###,###.###"
    val decimalFormat = DecimalFormat(pattern)

    return decimalFormat.format(this)
}
