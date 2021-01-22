package com.gigadrivegroup.kotlincommons.feature

import java.sql.Timestamp
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

public class Time(public val amount: Long, public val timeUnit: TimeUnit) {
    public val milliseconds: Long
        get() = amount * timeUnit.timeInMilliseconds

    public val ticks: Long
        get() = amount * timeUnit.timeInMilliseconds / 1000 * 20
}

public enum class TimeUnit(public val timeInMilliseconds: Long) {
    SECOND(1000),
    MINUTE(60 * 1000),
    HOUR(60 * 60 * 1000),
    DAY(24 * 60 * 60 * 1000),
    WEEK(7 * 24 * 60 * 60 * 100),
    MONTH(30 * 24 * 60 * 60 * 100)
}

public fun currentTimestamp(): Timestamp {
    val utc = ZonedDateTime.of(LocalDateTime.now(), ZoneId.of("UTC"))

    return Timestamp.valueOf(utc.toLocalDateTime())
}

public fun currentTimestamp(timeToAdd: Time): Timestamp? {
    var utc = ZonedDateTime.of(LocalDateTime.now(), ZoneId.of("UTC"))

    utc =
        when (timeToAdd.timeUnit) {
            TimeUnit.SECOND -> utc.plusSeconds(timeToAdd.amount)
            TimeUnit.MINUTE -> utc.plusMinutes(timeToAdd.amount)
            TimeUnit.HOUR -> utc.plusHours(timeToAdd.amount)
            TimeUnit.DAY -> utc.plusDays(timeToAdd.amount)
            TimeUnit.WEEK -> utc.plusWeeks(timeToAdd.amount)
            TimeUnit.MONTH -> utc.plusMonths(timeToAdd.amount)
        }

    return Timestamp.valueOf(utc.toLocalDateTime())
}

public fun currentDateTime(): ZonedDateTime {
    return ZonedDateTime.of(LocalDateTime.now(), ZoneId.of("UTC"))
}
