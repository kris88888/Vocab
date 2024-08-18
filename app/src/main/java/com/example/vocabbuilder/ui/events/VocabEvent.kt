package com.example.vocabbuilder.ui.events

import com.example.vocabbuilder.framework.data.VocabEntry

sealed interface VocabEvent {

    object SaveVocab: VocabEvent
    data class setWord(val word: String): VocabEvent
    data class setMeaning(val meaning: String): VocabEvent
    data class setUsage(val usage: String): VocabEvent
    data class setSynonym(val synonym: String): VocabEvent
    data class setAntonym(val antonym: String): VocabEvent
    object ShowDialog: VocabEvent
    object HideDialog: VocabEvent
    data class SortWords(val order: SortOrder): VocabEvent
    data class DeleteWord(val vocabEntry: VocabEntry): VocabEvent
}

enum class SortOrder { ASC, DESC, NONE}
