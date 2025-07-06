package com.thedaviddowdy.springboot_kotlin.presentation.dto

data class CreateArticleRequest(
    val title: String,
    val content: String
)