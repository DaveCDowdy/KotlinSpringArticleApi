package com.thedaviddowdy.springboot_kotlin.domain.port.`in`

import com.thedaviddowdy.springboot_kotlin.domain.Article

interface CreateArticleUseCase {
    fun create(command: CreateArticleCommand): Article
}

data class CreateArticleCommand(
    val title: String,
    val content: String
)