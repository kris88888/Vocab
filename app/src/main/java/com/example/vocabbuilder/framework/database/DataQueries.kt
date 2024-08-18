package com.example.vocabbuilder.framework.database

object DataQueries {

    const val get_all_vocab_words  = "select  * from VocabEntry"
    const val get_word = "select * from VocabEntry where word = :word"
}