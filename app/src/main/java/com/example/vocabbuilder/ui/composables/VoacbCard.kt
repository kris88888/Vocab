package com.example.vocabbuilder.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vocabbuilder.R
import com.example.vocabbuilder.framework.data.VocabEntry


@Composable
fun VocabCard(vocab: VocabEntry,
              onDelete: (VocabEntry) -> Unit,
              onEdit: (VocabEntry) -> Unit,
              onItemSelectedForEdit: () -> Unit = {}) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        var isEditMode by remember { mutableStateOf(false) }
        var editedWord by remember { mutableStateOf(vocab.copy()) }

        Row {
            Column(modifier = Modifier.weight(1f)) {

                if (!isEditMode) {
                    Text(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                        fontSize = 18.sp, text = vocab.word
                    )
                    Text(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                        fontSize = 12.sp, text = vocab.meaning
                    )
                    Text(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                        fontSize = 12.sp, text = "Sample Usage: ${vocab.sampleUsage}"
                    )
                    Text(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                        fontSize = 12.sp, text = "Synonyms: ${vocab.synonyms}"
                    )
                    Text(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                        fontSize = 12.sp, text = "Antonyms: ${vocab.antonyms}"
                    )
                } else {
                    LabeledEditText(
                        label = "Word",
                        value = editedWord.word,
                        onValueChange = {
                            editedWord = editedWord.copy(word = it)
                        },
                        maxLines = 1,
                        onDone = {
                            isEditMode = false
                            onEdit(editedWord)
                            onItemSelectedForEdit()
                        }
                    )
                    LabeledEditText(
                        label = "Meaning",
                        value = editedWord.meaning,
                        onValueChange = {
                            editedWord = editedWord.copy(meaning = it)
                        },
                        maxLines = 1,
                        onDone = {
                            isEditMode = false
                            onEdit(editedWord)
                            onItemSelectedForEdit()
                        }
                    )
                    LabeledEditText(
                        label = "Usage",
                        value = editedWord.sampleUsage,
                        onValueChange = {
                            editedWord = editedWord.copy(sampleUsage = it)
                        },
                        maxLines = 5,
                        onDone = {
                            isEditMode = false
                            onEdit(editedWord)
                            onItemSelectedForEdit()
                        }
                    )
                    LabeledEditText(
                        label = "Synonyms",
                        value = editedWord.synonyms,
                        onValueChange = {
                            editedWord = editedWord.copy(synonyms = it)
                        },
                        maxLines = 1,
                        onDone = {
                            isEditMode = false
                            onEdit(editedWord)
                            onItemSelectedForEdit()
                        }
                    )
                    LabeledEditText(
                        label = "Antonyms",
                        value = editedWord.antonyms,
                        onValueChange = {
                            editedWord = editedWord.copy(antonyms = it)
                        },
                        maxLines = 1,
                        onDone = {
                            isEditMode = false
                            onEdit(editedWord)
                            onItemSelectedForEdit()
                        }
                    )
                }
            }
            IconButton(onClick = {
                if (isEditMode) {
                    onEdit(editedWord)
                    onItemSelectedForEdit()
                } else {
                    onItemSelectedForEdit()
                }
                isEditMode = !isEditMode
            }) {
                Icon(
                    imageVector = if (!isEditMode) Icons.Default.Edit else Icons.Default.CheckCircle,
                    contentDescription = if (!isEditMode) stringResource(id = R.string.edit) else stringResource(
                        id = R.string.saveChanges
                    )
                )
            }
            IconButton(onClick = { onDelete(vocab) }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(id = R.string.delete)
                )
            }
        }
    }
}