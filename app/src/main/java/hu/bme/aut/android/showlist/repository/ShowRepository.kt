package hu.bme.aut.android.showlist.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import hu.bme.aut.android.showlist.ShowDao
import hu.bme.aut.android.showlist.database.RoomShow
import hu.bme.aut.android.showlist.model.Show
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ShowRepository (private val showDao: ShowDao)
{
    fun getAllShows(): LiveData<List<Show>>
    {
        return showDao.getAllShows()
            .map {roomShows ->
                roomShows.map {roomShow ->
                    roomShow.toDomainModel() }
            }
    }

    suspend fun insert(show: Show) = withContext(Dispatchers.IO) {
        showDao.insertShow(show.toRoomModel())
    }

    private fun RoomShow.toDomainModel(): Show
    {
        return Show(
            id = id,
            title = title,
            type = type,
            isWatched = isWatched,
            description = description,
            dueDate = dueDate,
            episode = episode
        )
    }

    private fun Show.toRoomModel(): RoomShow
    {
        return RoomShow(
            id = id,
            title = title,
            type = type,
            isWatched = isWatched,
            description = description,
            dueDate = dueDate,
            episode = episode
        )
    }
}