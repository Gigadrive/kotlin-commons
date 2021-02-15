package com.gigadrivegroup.kotlincommons.ext

public inline fun <reified E : Enum<E>> Enum<E>.safeValueOf(name: String): E? =
    enumValues<E>().find { it.name == name }
