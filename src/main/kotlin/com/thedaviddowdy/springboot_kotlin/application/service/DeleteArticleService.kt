package com.thedaviddowdy.springboot_kotlin.application.service

import com.thedaviddowdy.springboot_kotlin.domain.port.`in`.DeleteArticleUseCase
import com.thedaviddowdy.springboot_kotlin.domain.port.out.ArticleRepositoryPort
import org.springframework.stereotype.Service

@Service
class DeleteArticleService(
    private val articleRepositoryPort: ArticleRepositoryPort
) : DeleteArticleUseCase {

    override fun deleteBySlug(slug: String) {
        val existingArticleOptional = articleRepositoryPort.findBySlug(slug)

        if (existingArticleOptional.isEmpty) {
            throw NoSuchElementException("Article with slug '$slug' not found.")
        }

        articleRepositoryPort.delete(existingArticleOptional.get())
    }
}