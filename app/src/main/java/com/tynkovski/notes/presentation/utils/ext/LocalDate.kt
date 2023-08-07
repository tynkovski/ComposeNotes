package com.tynkovski.notes.presentation.utils.ext

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

fun LocalDateTime.daysBetween(date: LocalDateTime) = ChronoUnit.DAYS.between(this, date)
fun LocalDateTime.yearsBetween(date: LocalDateTime) = ChronoUnit.DAYS.between(this, date)

fun LocalDateTime.isYesterday(date: LocalDateTime) = daysBetween(date) == -1L
fun LocalDateTime.isSameDay(date: LocalDateTime) =  daysBetween(date) == 0L
fun LocalDateTime.isSameYear(date: LocalDateTime) = yearsBetween(date) == 0L
