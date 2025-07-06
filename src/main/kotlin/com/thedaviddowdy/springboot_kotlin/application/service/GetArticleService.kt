package com.thedaviddowdy.springboot_kotlin.application.service

import com.thedaviddowdy.springboot_kotlin.domain.Article
import com.thedaviddowdy.springboot_kotlin.domain.port.`in`.GetArticleUseCase
import com.thedaviddowdy.springboot_kotlin.domain.port.out.ArticleRepositoryPort
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class GetArticleService(
    private val articleRepositoryPort: ArticleRepositoryPort
) : GetArticleUseCase {

    override fun getAll(): Iterable<Article> {
        return articleRepositoryPort.findAllByOrderByCreatedAtDesc()
    }

    override fun getBySlug(slug: String): Optional<Article> {
        return articleRepositoryPort.findBySlug(slug)
    }
}