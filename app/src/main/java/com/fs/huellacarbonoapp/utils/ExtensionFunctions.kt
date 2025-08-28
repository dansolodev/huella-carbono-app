package com.fs.huellacarbonoapp.utils

import java.util.Locale

fun Double.formatWithTwoDecimalsToString(): String {
    return String.format(locale = Locale.ROOT, format = "%.02f", this)
}

fun Double?.orZero(): Double {
    return this ?: 0.0
}