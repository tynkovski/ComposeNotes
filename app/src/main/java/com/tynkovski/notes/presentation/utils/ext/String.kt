package com.tynkovski.notes.presentation.utils.ext

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.random.Random

fun String.fromISO(pattern: String): String {
    val date = LocalDateTime.parse(this, DateTimeFormatter.ISO_DATE_TIME)
    return date.format(DateTimeFormatter.ofPattern(pattern))
}

fun randomString(size: Int): String {
    val charSet = ('a'..'z') + ('A' .. 'Z')
    return buildString {
        repeat(size) {
            append(charSet[Random.nextInt(from = 1, until = charSet.lastIndex)])
        }
    }
}