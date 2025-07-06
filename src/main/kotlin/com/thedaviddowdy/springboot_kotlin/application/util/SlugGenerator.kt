package com.thedaviddowdy.springboot_kotlin.application.util

import java.util.*

object SlugGenerator {
    fun generateSlug(text: String): String {
        return text.lowercase(Locale.getDefault())
            .replace("\n", " ")
            .replace("[^a-z\\d\\s]".toRegex(), " ")
            .split(" ")
            .joinToString("-")
            .replace("-+".toRegex(), "-")
    }
}