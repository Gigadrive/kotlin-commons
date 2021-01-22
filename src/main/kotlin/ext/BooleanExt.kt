package com.gigadrivegroup.kotlincommons.ext

public fun Boolean.toInt(): Int {
    return if (this) {
        1
    } else {
        0
    }
}
