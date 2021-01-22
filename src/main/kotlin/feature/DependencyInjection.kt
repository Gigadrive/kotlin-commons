package com.gigadrivegroup.kotlincommons.feature

import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent

public inline fun <reified T> bind(instance: T) {
    loadKoinModules(module { single { instance } })
}

public inline fun <reified T : Any> inject(): Lazy<T> {
    val clazz: Class<out T> = T::class.java
    return lazy(LazyThreadSafetyMode.SYNCHRONIZED) { KoinJavaComponent.get(clazz, null, null) }
}

public fun initDependencyInjection() {
    startKoin {
        printLogger()
        modules()
    }
}
