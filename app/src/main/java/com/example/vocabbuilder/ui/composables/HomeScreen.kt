package com.example.vocabbuilder.ui.composables

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vocabbuilder.framework.data.VocabEntry
import com.example.vocabbuilder.ui.data.VocabUiState
import com.example.vocabbuilder.ui.events.SortOrder
import com.example.vocabbuilder.ui.events.VocabEvent
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    uiState: VocabUiState,
    onEvent: (VocabEvent) -> Unit
) {
    val wordsList = uiState.wordsList
    var isWordSavedOrCancelCalled by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true,
        confirmValueChange = {
            Log.d("*******", "confirm called, ${isWordSavedOrCancelCalled}, ${it}")
            when(it) {
                SheetValue.Hidden -> isWordSavedOrCancelCalled
                SheetValue.Expanded -> true
                else -> false
            }
        }
    )
    val scope = rememberCoroutineScope()
    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            SortOptionsRow(uiState, onEvent)
            Spacer(modifier = Modifier.height(20.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(wordsList) {
                    VocabCard(vocab = it) { vocabEntry ->
                        onEvent(VocabEvent.DeleteWord(vocabEntry))
                    }
                }
            }
            if (sheetState.currentValue != SheetValue.Hidden) {
                CreateWordBottomSheet(sheetState = sheetState,
                    onDismiss = {
                        isWordSavedOrCancelCalled = true
                        scope.launch { sheetState.hide() }
                        Log.d("*******", "HomeScreen: onDismissed, ${sheetState.currentValue}")
                    },
                    onEvent = onEvent,
                    onSave = {
                        isWordSavedOrCancelCalled = true
                        scope.launch { sheetState.hide() }
                        onEvent(VocabEvent.SaveVocab)
                        Log.d("*******", "HomeScreen: onSaved , Sheetstate current value = ${sheetState.currentValue}")
                    }
                )
            }
        }
        Box(
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.BottomEnd)
                .padding(24.dp)
        ) {
            FloatingActionButton(onClick = {
                scope.launch {
                 isWordSavedOrCancelCalled = false
                 sheetState.show()
                }
            }) {
                Icon(Icons.Filled.Add, contentDescription = "Floating Action For Add Word")
            }
        }
    }
}

@Composable
fun SortOptionsRow(uiState: VocabUiState, onEvent: (VocabEvent) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .horizontalScroll(
                rememberScrollState()
            )
    ) {
        SortOrder.entries.forEach {
            Row(modifier = Modifier
                .wrapContentSize()
                .clickable {
                    onEvent(VocabEvent.SortWords(it))
                }) {
                RadioButton(
                    selected = uiState.sortOrder == it,
                    onClick = { onEvent(VocabEvent.SortWords(it)) })
                Text(text = it.name)
            }
        }
    }
}


@Composable
fun VocabCard(vocab: VocabEntry, onDelete: (VocabEntry) -> Unit) {
    Card(onClick = {}) {
        Row {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    fontSize = 18.sp, text = vocab.word
                )
                Text(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    fontSize = 12.sp, text = vocab.meaning
                )
                Text(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    fontSize = 12.sp, text = "Sample Usage: ${vocab.sampleUsage}"
                )
                Text(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    fontSize = 12.sp, text = "Synonyms: ${vocab.synonyms}"
                )
                Text(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    fontSize = 12.sp, text = "Antonyms: ${vocab.antonyms}"
                )
            }
            IconButton(onClick = { onDelete(vocab) }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete"
                )
            }
        }
    }
}