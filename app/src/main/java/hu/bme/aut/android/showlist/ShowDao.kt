package hu.bme.aut.android.showlist

import androidx.lifecycle.LiveData
import androidx.room.*
import hu.bme.aut.android.showlist.database.RoomShow

@Dao
interface ShowDao
{
    @Insert
    fun insertShow(show: RoomShow)

    @Query("SELECT * FROM show_table")
    fun getAllShows(): LiveData<List<RoomShow>>

    @Update
    fun updateShow(show: RoomShow): Int

    @Delete
    fun deleteShow(show: RoomShow)

    @Query("SELECT * FROM show_table WHERE id == :id")
    fun getShowById(id: Int?): RoomShow?
}