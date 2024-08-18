package com.example.core.usecases

import com.example.core.data.WordRepository
import com.example.core.domain.Word

class GetAllWordsUseCase(private val wordRepository: WordRepository) {

    fun invoke(): List<Word> {
        // Add Word to the database via wordRepository.
        return emptyList()
    }
}