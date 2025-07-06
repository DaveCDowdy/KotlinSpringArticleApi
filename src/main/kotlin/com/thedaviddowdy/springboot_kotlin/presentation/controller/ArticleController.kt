package com.thedaviddowdy.springboot_kotlin.presentation.controller

import com.thedaviddowdy.springboot_kotlin.domain.port.`in`.*
import com.thedaviddowdy.springboot_kotlin.presentation.dto.ArticleDto
import com.thedaviddowdy.springboot_kotlin.presentation.dto.CreateArticleRequest
import com.thedaviddowdy.springboot_kotlin.presentation.dto.UpdateArticleRequest
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.NoSuchElementException

@RestController
@RequestMapping("api/v1/articles")
class ArticleController(
    private val getArticleUseCase: GetArticleUseCase,
    private val createArticleUseCase: CreateArticleUseCase,
    private val updateArticleUseCase: UpdateArticleUseCase,
    private val deleteArticleUseCase: DeleteArticleUseCase
) {

    @GetMapping
    fun getAllArticles(): Iterable<ArticleDto> {
        return getArticleUseCase.getAll().map { ArticleDto.fromDomain(it) }
    }

    @GetMapping("/{slug}")
    fun getArticleBySlug(@PathVariable slug: String): ArticleDto {
        return getArticleUseCase.getBySlug(slug)
            .map { ArticleDto.fromDomain(it) }
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Article with slug '$slug' not found.") }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createArticle(@RequestBody request: CreateArticleRequest): ArticleDto {
        val command = CreateArticleCommand(
            title = request.title,
            content = request.content
        )
        return try {
            val createdArticle = createArticleUseCase.create(command)
            ArticleDto.fromDomain(createdArticle)
        } catch (e: IllegalArgumentException) {
            // This catch is for when a duplicate slug is attempted, as per CreateArticleService
            throw ResponseStatusException(HttpStatus.CONFLICT, e.message)
        }
    }

    @PutMapping("/{slug}")
    fun updateArticle(@PathVariable slug: String, @RequestBody request: UpdateArticleRequest): ArticleDto {
        val command = UpdateArticleCommand(
            title = request.title,
            content = request.content
        )
        return try {
            val updatedArticle = updateArticleUseCase.update(slug, command)
            ArticleDto.fromDomain(updatedArticle)
        } catch (e: NoSuchElementException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        } catch (e: IllegalArgumentException) {
            // This catch is for when an updated slug results in a conflict
            throw ResponseStatusException(HttpStatus.CONFLICT, e.message)
        }
    }

    @DeleteMapping("/{slug}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteArticle(@PathVariable slug: String) {
        try {
            deleteArticleUseCase.deleteBySlug(slug)
        } catch (e: NoSuchElementException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }
    }
}