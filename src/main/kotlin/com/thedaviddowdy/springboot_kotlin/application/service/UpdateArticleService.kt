package com.thedaviddowdy.springboot_kotlin.application.service

import com.thedaviddowdy.springboot_kotlin.domain.Article
import com.thedaviddowdy.springboot_kotlin.domain.port.`in`.UpdateArticleCommand
import com.thedaviddowdy.springboot_kotlin.domain.port.`in`.UpdateArticleUseCase
import com.thedaviddowdy.springboot_kotlin.domain.port.out.ArticleRepositoryPort
import org.springframework.stereotype.Service
import com.thedaviddowdy.springboot_kotlin.application.util.SlugGenerator
import java.util.*

@Service
class UpdateArticleService(
    private val articleRepositoryPort: ArticleRepositoryPort
) : UpdateArticleUseCase {

    override fun update(slug: String, command: UpdateArticleCommand): Article {
        val existingArticleOptional = articleRepositoryPort.findBySlug(slug)

        if (existingArticleOptional.isEmpty) {
            throw NoSuchElementException("Article with slug '$slug' not found.")
        }

        val existingArticle = existingArticleOptional.get()
        val newSlug = SlugGenerator.generateSlug(command.title)

        // Check if slug changed and if new slug already exists for another article
        if (newSlug != existingArticle.slug && articleRepositoryPort.existsBySlug(newSlug)) {
            throw IllegalArgumentException("Article with new slug '$newSlug' already exists.")
        }

        // Create a new Article instance with updated fields, preserving original ID and createdAt
        val updatedArticle = existingArticle.copy(
            title = command.title,
            content = command.content,
            slug = newSlug,
            createdAt = existingArticle.createdAt // Preserve original creation date
        )

        return articleRepositoryPort.save(updatedArticle)
    }
}