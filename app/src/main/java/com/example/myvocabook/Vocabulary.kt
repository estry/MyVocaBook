package com.example.myvocabook

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Vocabulary(
    @PrimaryKey @ColumnInfo(name = "Index") val index: Long?,
    @ColumnInfo(name = "Day") var day: String?,
    @ColumnInfo(name = "Word") var word: String?,
    @ColumnInfo(name = "Meaning") var meaning: String?,
    @ColumnInfo(name = "IsBookmark") var isBookmark: Boolean?
)