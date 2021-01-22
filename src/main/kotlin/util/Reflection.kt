package com.gigadrivegroup.kotlincommons.util

import java.lang.reflect.Field

public fun field(obj: Any, name: String): Any? {
    return try {
        val field: Field = obj.javaClass.getDeclaredField(name)
        field.isAccessible = true
        field.get(obj)
    } catch (e: Exception) {
        throw RuntimeException("Unable to retrieve field content.", e)
    }
}
