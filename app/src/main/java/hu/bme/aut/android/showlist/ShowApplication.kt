package hu.bme.aut.android.showlist

import android.app.Application
import androidx.room.Room
import hu.bme.aut.android.showlist.database.ShowDatabase
import hu.bme.aut.android.showlist.notification.ShowNotificationHelper

class ShowApplication: Application()
{
    companion object
    {
        lateinit var showDatabase: ShowDatabase
            private set
    }

    override fun onCreate()
    {
        super.onCreate()

        showDatabase = Room.databaseBuilder(
            applicationContext,
            ShowDatabase::class.java,
            "show_database"
        ).fallbackToDestructiveMigration().build()
        ShowNotificationHelper.createNotificationChannels(this)
    }
}