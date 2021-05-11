package hu.bme.aut.android.showlist.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "show")
data class RoomShow(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val type: String,
    val isWatched: Boolean,
    val description: String,
    val dueDate: String,
    val episode: String
)