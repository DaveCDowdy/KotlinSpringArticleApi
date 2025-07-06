package com.thedaviddowdy.springboot_kotlin.domain

import java.time.LocalDateTime

data class Article(
    val id: Long? = null,
    val title: String,
    val content: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val slug: String
)