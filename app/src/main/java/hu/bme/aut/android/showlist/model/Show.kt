package hu.bme.aut.android.showlist.model

import java.util.*

data class Show(
    val id: Int,
    val title: String,
    val type: String,
    val isWatched: Boolean,
    val description: String,
    val dueDate: Date,
    val episode: String
)