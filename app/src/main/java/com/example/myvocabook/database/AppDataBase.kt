package com.example.myvocabook.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Vocabulary::class], version = 2)
abstract class AppDataBase : RoomDatabase() {
    abstract fun vocabularyDao(): VocabularyDao

    companion object {
        private const val DATABASE_NAME = "myvoca.db"

        fun getInstance(context: Context): AppDataBase {
            return Room
                .databaseBuilder(context, AppDataBase::class.java,
                    DATABASE_NAME
                )
                .build()
        }

    }
}