package com.tynkovski.notes.presentation.utils.ext

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit

val TextStyle.semiBold @Composable get() = copy(fontWeight = FontWeight.SemiBold)
val TextStyle.bold @Composable get() = copy(fontWeight = FontWeight.Bold)
val TextStyle.black @Composable get() = copy(fontWeight = FontWeight.Black)
val TextStyle.medium @Composable get() = copy(fontWeight = FontWeight.Medium)
val TextStyle.light @Composable get() = copy(fontWeight = FontWeight.Light)
val TextStyle.thin @Composable get() = copy(fontWeight = FontWeight.Thin)
val TextStyle.normal @Composable get() = copy(fontWeight = FontWeight.Normal)

@Composable
fun TextStyle.size(fontSize: TextUnit) = copy(fontSize = fontSize)

@Composable
fun TextStyle.lettering(letterSpacing: TextUnit) = copy(letterSpacing = letterSpacing)

@Composable
fun TextStyle.color(color: Color) = copy(color = color)
