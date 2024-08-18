package com.example.core.usecases

import com.example.core.data.WordRepository

class AddWordUseCase(private val wordRepository: WordRepository) {

    fun invoke(word: String, meaning: String, usage: String) {
        // Add Word to the database via wordRepository.
    }
}