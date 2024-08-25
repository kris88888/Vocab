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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
            when (it) {
                SheetValue.Hidden -> isWordSavedOrCancelCalled
                SheetValue.Expanded -> true
                else -> false
            }
        }
    )
    val scope = rememberCoroutineScope()
    var selectedIndexForEdit: Int by remember { mutableStateOf(-1) }
    Log.d("&&&&&", "***** NOW VALUE = ${selectedIndexForEdit}")

    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            val listState = rememberLazyListState()

            SortOptionsRow(uiState, selectedIndexForEdit) { event ->
                Log.d("&&&&&", "***** IS EDITE MODE = ${selectedIndexForEdit}")
                onEvent(event)
                scope.launch {
                    listState.animateScrollToItem(0)
                }

            }
            Spacer(modifier = Modifier.height(20.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                state = listState
            ) {
                itemsIndexed(items = wordsList, { index, item -> item.word }) { index, item ->
                    VocabCard(
                        vocab = item,
                        onDelete = { vocabEntry ->
                            onEvent(VocabEvent.DeleteWord(vocabEntry))
                        },
                        onEdit = { vocabEntry ->
                            selectedIndexForEdit = -1
                            onEvent(VocabEvent.EditWord(vocabEntry))
                        },
                        onItemSelectedForEdit = {
                            selectedIndexForEdit = index
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
            if (sheetState.currentValue != SheetValue.Hidden) {
                CreateWordBottomSheet(sheetState = sheetState,
                    onDismiss = {
                        isWordSavedOrCancelCalled = true
                        scope.launch { sheetState.hide() }
                    },
                    onEvent = onEvent,
                    onSave = {
                        isWordSavedOrCancelCalled = true
                        scope.launch { sheetState.hide() }
                        onEvent(VocabEvent.SaveVocab)
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
fun SortOptionsRow(
    uiState: VocabUiState,
    isEditInProgress: Int?,
    onEvent: (VocabEvent) -> Unit
) {
    Log.d("&&&&", "SortOptionsRow INDEX VALUE : ${isEditInProgress}")
    val isEditing by remember {
        derivedStateOf { isEditInProgress != null }
    }
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
                    if (!isEditing) {
                        onEvent(VocabEvent.SortWords(it))
                    }
                }) {
                RadioButton(
                    selected = uiState.sortOrder == it,
                    onClick = {
                        if (!isEditing) {
                            onEvent(VocabEvent.SortWords(it))
                        }
                    })
                Text(text = it.name)
            }
        }
    }
}