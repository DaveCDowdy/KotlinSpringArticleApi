package com.thedaviddowdy.springboot_kotlin.application.service

import com.thedaviddowdy.springboot_kotlin.application.util.SlugGenerator
import com.thedaviddowdy.springboot_kotlin.domain.Article
import com.thedaviddowdy.springboot_kotlin.domain.port.`in`.CreateArticleCommand
import com.thedaviddowdy.springboot_kotlin.domain.port.`in`.CreateArticleUseCase
import com.thedaviddowdy.springboot_kotlin.domain.port.out.ArticleRepositoryPort
import org.springframework.stereotype.Service

@Service
class CreateArticleService(
    private val articleRepositoryPort: ArticleRepositoryPort
) : CreateArticleUseCase {

    override fun create(command: CreateArticleCommand): Article {
        // Generate slug here, before creating the domain Article
        val slug = SlugGenerator.generateSlug(command.title)
        // Check for existing slug to prevent duplicates
        if (articleRepositoryPort.existsBySlug(slug)) {
            throw IllegalArgumentException("Article with slug '$slug' already exists.")
        }

        val article = Article(
            title = command.title,
            content = command.content,
            slug = slug
        )
        return articleRepositoryPort.save(article)
    }
}