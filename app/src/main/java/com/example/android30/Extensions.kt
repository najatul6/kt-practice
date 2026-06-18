package com.example.android30

fun String.capitalizeFirst(): String {
    return replaceFirstChar {
        it.uppercase()
    }
}