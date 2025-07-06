package com.thedaviddowdy.springboot_kotlin.presentation.dto

import com.thedaviddowdy.springboot_kotlin.domain.Article
import java.time.LocalDateTime

data class ArticleDto(
    val id: Long?,
    val title: String,
    val content: String,
    val createdAt: LocalDateTime,
    val slug: String
) {
    companion object {
        fun fromDomain(article: Article): ArticleDto {
            return ArticleDto(
                id = article.id,
                title = article.title,
                content = article.content,
                createdAt = article.createdAt,
                slug = article.slug
            )
        }
    }
}