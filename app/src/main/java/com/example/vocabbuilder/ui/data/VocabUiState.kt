package com.example.vocabbuilder.ui.data

import com.example.vocabbuilder.framework.data.VocabEntry
import com.example.vocabbuilder.ui.events.SortOrder

data class VocabUiState(
    val wordsList : List<VocabEntry> = emptyList(),
    val word: String = "",
    val meaning: String = "",
    val usage: String = "",
    val synonyms: String = "",
    val antonyms: String = "",
    val isAddingWord: Boolean = false,
    val sortOrder: SortOrder = SortOrder.ASC
)
