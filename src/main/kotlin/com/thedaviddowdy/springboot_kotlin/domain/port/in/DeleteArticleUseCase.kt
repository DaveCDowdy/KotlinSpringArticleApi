package com.thedaviddowdy.springboot_kotlin.domain.port.`in`

interface DeleteArticleUseCase {
    fun deleteBySlug(slug: String)
}