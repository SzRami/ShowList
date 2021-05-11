package hu.bme.aut.android.showlist.model

data class Show(
    val id: Int,
    val title: String,
    val type: String,
    val isWatched: Boolean,
    val description: String,
    val dueDate: String,
    val episode: String
)