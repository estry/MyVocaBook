package com.example.myvocabook

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Vocabulary::class], version = 2)
abstract class AppDataBase : RoomDatabase() {
    abstract fun vocabularyDao(): VocabularyDao

    companion object {
        /*private var INSTANCE: AppDataBase? = null
        fun getInstance(context: Context): AppDataBase? {
            if (INSTANCE == null) {
                synchronized(AppDataBase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDataBase::class.java,
                        "voca.db"
                    )
                        .createFromAsset("voca.db")
                        .build()
                }
            }
            return INSTANCE*/
        private const val DATABASE_NAME = "myvoca.db"
        private const val DATABASE_DIR = "voca.db" // Asset/database/you_name.db

        fun getInstance(context: Context): AppDataBase {
            return Room
                .databaseBuilder(context, AppDataBase::class.java, DATABASE_NAME)
                .build()
        }

    }
}