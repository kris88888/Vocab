package com.example.vocabbuilder.ui.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabeledEditText(
    label: String,
    value: String,
    maxLines: Int = 1,
    fontSize: TextUnit = 12.sp,
    onValueChange: (String) -> Unit,
    onDone: () -> Unit
) {
    TextField(
        modifier = Modifier
            .wrapContentSize()
            .padding(0.dp),
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(text = label)
        },
        maxLines = maxLines,
        textStyle = TextStyle(fontSize = fontSize),
        keyboardActions = KeyboardActions(onDone = {
            onDone()
        }),
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done)
    )
}