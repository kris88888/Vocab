package com.example.vocabbuilder.ui.composables

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.vocabbuilder.framework.data.VocabEntry
import com.example.vocabbuilder.ui.events.VocabEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateWordBottomSheet(
    sheetState: SheetState,
    onDismiss: () -> Unit,
    onSave: () -> Unit,
    onEvent: (word: VocabEvent) -> Unit, // todo check if this fn is really required
    word: VocabEntry? = null
) {
    var wordState by remember {
        mutableStateOf(
            word ?: VocabEntry(
                word = "",
                meaning = "",
                sampleUsage = ""
            )
        )
    }

    ModalBottomSheet(onDismissRequest = {
        Log.d("**************", "CreateWordBottomSheet: onDismiss called")
        onDismiss()
    },
        sheetState = sheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() }) {
        Column(
            modifier = Modifier.navigationBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                placeholder = { Text("Enter word") },
                value = wordState.word,
                onValueChange = {
                    onEvent(VocabEvent.setWord(it))
                    wordState = wordState.copy(word = it)
                },
                label = { Text("Word") },
                singleLine = true
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
            )
            TextField(
                placeholder = { Text("Enter Meaning") },
                value = wordState.meaning,
                onValueChange = {
                    onEvent(VocabEvent.setMeaning(it))
                    wordState = wordState.copy(meaning = it)
                },
                label = { Text("Meaning") },
                singleLine = true
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
            )
            TextField(
                placeholder = { Text("Example Usages") },
                value = wordState.sampleUsage,
                onValueChange = {
                    onEvent(VocabEvent.setUsage(it))

                    wordState = wordState.copy(sampleUsage = it)
                },
                label = { Text("Example") },
                singleLine = false,
                maxLines = 5
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
            )
            TextField(
                placeholder = { Text("Synonyms") },
                value = wordState.synonyms,
                onValueChange = {
                    onEvent(VocabEvent.setSynonym(it))
                    wordState = wordState.copy(synonyms = it)
                },
                label = { Text("Synonyms") },
                singleLine = false,
                maxLines = 2
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
            )
            TextField(
                placeholder = { Text("Antonyms") },
                value = wordState.antonyms,
                onValueChange = {
                    onEvent(VocabEvent.setAntonym(it))
                    wordState = wordState.copy(antonyms = it)
                },
                label = { Text("Antonyms") },
                singleLine = false,
                maxLines = 2
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
            )

            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                Button(onClick = { onDismiss() }) {
                    Text(text = "Cancel")
                }
                Button(onClick = { onSave() }) {
                    Text(text = "Save")
                }
            }
        }
    }
}
