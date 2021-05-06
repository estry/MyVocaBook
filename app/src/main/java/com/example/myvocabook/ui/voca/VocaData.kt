package com.example.myvocabook.ui.voca

data class VocaData(
    var index: Long,
    var day: String, var word: String, var meaning: String,
    var isBookmark: Boolean? = false, var isOpen: Boolean = false
)