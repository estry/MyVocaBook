package com.example.myvocabook.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface VocabularyDao {
    @Query("select * from Vocabulary")
    suspend fun getAll(): List<Vocabulary>

    @Query("select * from Vocabulary where Day is :selectDay")
    suspend fun getWordFromDay(selectDay: String): List<Vocabulary>

    @Query("select distinct Day from Vocabulary")
    suspend fun getDay(): List<Vocabulary>

    @Query("select * from Vocabulary where IsBookmark = 1")
    suspend fun getAllByBookmark(): List<Vocabulary>

    @Insert
    suspend fun insert(vocabularyTable: Vocabulary)

    @Update
    suspend fun updateBookmark(markedVoca: Vocabulary): Int

    @Query("delete from Vocabulary")
    suspend fun deleteAll()
}