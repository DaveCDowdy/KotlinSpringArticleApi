package com.thedaviddowdy.springboot_kotlin.domain.port.out

import com.thedaviddowdy.springboot_kotlin.domain.Article
import java.util.Optional

interface ArticleRepositoryPort {
    fun findAllByOrderByCreatedAtDesc(): Iterable<Article>
    fun findBySlug(slug: String): Optional<Article>
    fun save(article: Article): Article
    fun delete(article: Article)
    fun existsBySlug(slug: String): Boolean
}