package com.thedaviddowdy.springboot_kotlin.domain.port.`in`

import com.thedaviddowdy.springboot_kotlin.domain.Article
import java.util.Optional

interface GetArticleUseCase {
    fun getAll(): Iterable<Article>
    fun getBySlug(slug: String): Optional<Article>
}