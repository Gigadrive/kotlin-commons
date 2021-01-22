package com.gigadrivegroup.kotlincommons.feature

import java.util.ArrayList
import java.util.Random

public fun randomInteger(min: Int, max: Int): Int {
    return Random().nextInt((max - min) + 1) + min
}

public fun randomDouble(min: Double, max: Double): Double {
    return min + (max - min) * Random().nextDouble()
}

public fun chance(chanceTrue: Int, chanceFalse: Int): Boolean {
    val a: ArrayList<Boolean> = arrayListOf()

    for (i in 0 until chanceTrue) a.add(java.lang.Boolean.TRUE)
    for (i in 0 until chanceFalse) a.add(java.lang.Boolean.FALSE)

    a.shuffle()

    return a[0]
}

/**
 * Generates a chance based on probability<br></br>See https://stackoverflow.com/a/8183871/4117923
 *
 * @param chance The probability to use, provided in a floating value. Setting this to 0.04 means
 * there's a 4% chance of the end result being true
 * @return The end result
 */
public fun chance(chance: Double): Boolean {
    return chance(chance.toFloat())
}

/**
 * Generates a chance based on probability<br></br>See https://stackoverflow.com/a/8183871/4117923
 *
 * @param chance The probability to use, provided in a floating value. Setting this to 0.04 means
 * there's a 4% chance of the end result being true
 * @return The end result
 */
public fun chance(chance: Float): Boolean {
    return Random().nextDouble() <= chance
}

public fun max(number1: Int, number2: Int): Int = number1.coerceAtLeast(number2)

public fun max(number1: Float, number2: Float): Float = number1.coerceAtLeast(number2)

public fun max(number1: Double, number2: Double): Double = number1.coerceAtLeast(number2)

public fun min(number1: Int, number2: Int): Int = number1.coerceAtMost(number2)

public fun min(number1: Float, number2: Float): Float = number1.coerceAtMost(number2)

public fun min(number1: Double, number2: Double): Double = number1.coerceAtMost(number2)
