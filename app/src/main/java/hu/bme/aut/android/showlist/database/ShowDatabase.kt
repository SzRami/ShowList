package hu.bme.aut.android.showlist.database

import androidx.room.Database
import androidx.room.RoomDatabase
import hu.bme.aut.android.showlist.ShowDao

@Database(
    version = 3,
    exportSchema = false,
    entities = [RoomShow::class]
)

abstract class ShowDatabase : RoomDatabase()
{
    abstract fun showDao(): ShowDao
}