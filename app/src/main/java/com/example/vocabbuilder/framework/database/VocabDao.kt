package com.example.vocabbuilder.framework.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.vocabbuilder.framework.data.VocabEntry
import kotlinx.coroutines.flow.Flow


@Dao
interface VocabDao {

    @Upsert
    suspend fun upsertWord(word: VocabEntry)

    @Delete
    suspend fun deleteWord(word:VocabEntry)

    @Update
    fun modifyWord(word: VocabEntry)

    @Query(DataQueries.get_word)
    fun getWord(word: String): Flow<VocabEntry>

    @Query(DataQueries.get_all_vocab_words)
    fun getAllWords(): Flow<List<VocabEntry>>
}


