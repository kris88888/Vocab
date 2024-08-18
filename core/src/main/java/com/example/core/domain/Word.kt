package com.example.core.domain

import java.util.UUID

data class Word(
    val id: String = UUID.randomUUID().toString(),
    val word: String,
    val meaning: String,
    val usage: String,
    val language: Language = Language.English
)

enum class Language { English, French }