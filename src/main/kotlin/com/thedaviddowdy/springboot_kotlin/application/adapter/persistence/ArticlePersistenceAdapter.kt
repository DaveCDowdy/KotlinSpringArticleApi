package com.thedaviddowdy.springboot_kotlin.application.adapter.persistence

import com.thedaviddowdy.springboot_kotlin.application.adapter.persistence.entity.ArticleJpaEntity
import com.thedaviddowdy.springboot_kotlin.application.adapter.persistence.repository.SpringArticleRepository
import com.thedaviddowdy.springboot_kotlin.domain.Article
import com.thedaviddowdy.springboot_kotlin.domain.port.out.ArticleRepositoryPort
import org.springframework.stereotype.Component
import java.util.Optional

@Component
class ArticlePersistenceAdapter(
    private val springArticleRepository: SpringArticleRepository
) : ArticleRepositoryPort {

    override fun findAllByOrderByCreatedAtDesc(): Iterable<Article> {
        return springArticleRepository.findAllByOrderByCreatedAtDesc().map { it.toDomain() }
    }

    override fun findBySlug(slug: String): Optional<Article> {
        return springArticleRepository.findBySlug(slug).map { it.toDomain() }
    }

    override fun save(article: Article): Article {
        val jpaEntity = article.toJpaEntity()
        val savedEntity = springArticleRepository.save(jpaEntity)
        return savedEntity.toDomain()
    }

    override fun delete(article: Article) {
        val jpaEntity = article.toJpaEntity()
        springArticleRepository.delete(jpaEntity)
    }

    override fun existsBySlug(slug: String): Boolean {
        return springArticleRepository.existsBySlug(slug)
    }

    // --- Mapping functions ---
    // These can be in a separate mapper utility if they get complex
    private fun ArticleJpaEntity.toDomain(): Article {
        return Article(
            id = this.id,
            title = this.title,
            content = this.content,
            createdAt = this.createdAt,
            slug = this.slug
        )
    }

    private fun Article.toJpaEntity(): ArticleJpaEntity {
        return ArticleJpaEntity(
            id = this.id,
            title = this.title,
            content = this.content,
            createdAt = this.createdAt,
            slug = this.slug
        )
    }
}