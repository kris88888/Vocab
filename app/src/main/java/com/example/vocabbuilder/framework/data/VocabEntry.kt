package com.example.vocabbuilder.framework.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class VocabEntry(
    @PrimaryKey(autoGenerate = false)
    val word: String,
    val meaning: String,
    val sampleUsage:String,
    val synonyms: String = "",
    val antonyms: String = ""
)
