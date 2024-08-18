package com.example.vocabbuilder.framework.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.vocabbuilder.framework.data.VocabEntry


@Database(
    entities = [VocabEntry::class],
    version = 1
)
abstract class VocabDatabase: RoomDatabase() {

    abstract val dao: VocabDao
}