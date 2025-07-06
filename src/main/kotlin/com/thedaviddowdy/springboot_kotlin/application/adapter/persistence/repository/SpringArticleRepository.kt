package com.thedaviddowdy.springboot_kotlin.application.adapter.persistence.repository

import com.thedaviddowdy.springboot_kotlin.application.adapter.persistence.entity.ArticleJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface SpringArticleRepository : JpaRepository<ArticleJpaEntity, Long> {
    fun findAllByOrderByCreatedAtDesc(): Iterable<ArticleJpaEntity>
    fun findBySlug(slug: String): Optional<ArticleJpaEntity>
    fun existsBySlug(slug: String): Boolean
}