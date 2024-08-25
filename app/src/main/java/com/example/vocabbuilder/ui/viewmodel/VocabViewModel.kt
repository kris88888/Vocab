package com.example.vocabbuilder.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vocabbuilder.framework.data.VocabEntry
import com.example.vocabbuilder.framework.database.VocabDao
import com.example.vocabbuilder.ui.data.VocabUiState
import com.example.vocabbuilder.ui.events.SortOrder
import com.example.vocabbuilder.ui.events.VocabEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class VocabViewModel(
    private val dao: VocabDao
) : ViewModel() {

    private val TAG = "VocabViewModel"
    private val _sortOrder = MutableStateFlow(SortOrder.NONE)
    private val _state = MutableStateFlow(VocabUiState())

    var uiState = combine(_state, _sortOrder) { vocabState, sortOrder ->
        val sortedList = when (_sortOrder.value) {
            SortOrder.ASC -> _state.value.wordsList.sortedBy { it.word }
            SortOrder.DESC -> _state.value.wordsList.sortedByDescending { it.word }
            SortOrder.NONE -> _state.value.wordsList
        }

        vocabState.copy(
            wordsList = sortedList, sortOrder = sortOrder
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _state.value)

    init {
        viewModelScope.launch {
            dao.getAllWords().collectLatest { vocabList ->
                _state.update {
                    it.copy(wordsList = vocabList)
                }
            }
        }
    }

    fun onEvent(event: VocabEvent) {
        Log.d(TAG, "onEvent: $event")
        when (event) {
            is VocabEvent.DeleteWord -> {
                viewModelScope.launch {
                    dao.deleteWord(event.vocabEntry)
                }

            }

            VocabEvent.HideDialog -> {
                _state.update {
                    it.copy(
                        isAddingWord = false
                    )
                }
            }

            VocabEvent.SaveVocab -> {
                Log.d(TAG, "onEvent: GOING TO SAVE = ${_state.value}")
                viewModelScope.launch {
                    dao.upsertWord(
                        VocabEntry(
                            word = _state.value.word,
                            meaning = _state.value.meaning,
                            sampleUsage = _state.value.usage,
                            synonyms = _state.value.synonyms,
                            antonyms = _state.value.antonyms
                        )
                    )
                }
                _state.update {
                    it.copy(
                        isAddingWord = false,
                        word = "",
                        meaning = "",
                        usage = "",
                        synonyms = "",
                        antonyms = ""
                    )
                }
            }

            VocabEvent.ShowDialog -> {
                _state.update {
                    it.copy(isAddingWord = true)
                }
            }

            is VocabEvent.SortWords -> {
                _sortOrder.value = event.order
            }

            is VocabEvent.setAntonym -> {
                _state.update {
                    it.copy(antonyms = event.antonym)
                }
            }

            is VocabEvent.setSynonym -> {
                _state.update {
                    it.copy(synonyms = event.synonym)
                }
            }

            is VocabEvent.setUsage -> {
                _state.update {
                    it.copy(usage = event.usage)
                }
            }

            is VocabEvent.setMeaning -> {
                _state.update {
                    it.copy(meaning = event.meaning)
                }
            }

            is VocabEvent.setWord -> {
                _state.update {
                    it.copy(word = event.word)
                }
            }

            is VocabEvent.EditWord -> {
                Log.d("****", "* SAVED = ${event.vocabEntry}")
                viewModelScope.launch {
                    dao.upsertWord(event.vocabEntry)
                }
                _state.update {
                    it.copy(
                        isAddingWord = false,
                        word = "",
                        meaning = "",
                        usage = "",
                        synonyms = "",
                        antonyms = ""
                    )
                }

            }
        }
    }
}