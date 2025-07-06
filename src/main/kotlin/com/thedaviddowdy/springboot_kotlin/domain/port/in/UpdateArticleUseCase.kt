package com.thedaviddowdy.springboot_kotlin.domain.port.`in`

import com.thedaviddowdy.springboot_kotlin.domain.Article

interface UpdateArticleUseCase {
    fun update(slug: String, command: UpdateArticleCommand): Article
}

data class UpdateArticleCommand(
    val title: String,
    val content: String
)