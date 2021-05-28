package hu.bme.aut.android.showlist.notification

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import hu.bme.aut.android.showlist.R
import hu.bme.aut.android.showlist.ShowListActivity
import hu.bme.aut.android.showlist.model.Show
import java.util.*
import kotlin.random.Random

class ShowNotificationHelper
{
    companion object
    {
        const val SHOW_ID = "SHOW_ID"
        const val SHOW_TITLE = "SHOW_TITLE"

        fun createNotificationChannels(ctx: Context)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            {
                ShowNotificationChannel.values().forEach {
                    val name = it.channelName
                    val descriptionText = it.channelDescription
                    val importance = NotificationManager.IMPORTANCE_DEFAULT
                    val channel = NotificationChannel(it.id, name, importance).apply {
                        description = descriptionText
                    }
                    with(NotificationManagerCompat.from(ctx)) {
                        createNotificationChannel(channel)
                    }
                }
            }
        }

        fun createToWatchNotification(context: Context, show: Show)
        {
            val intent = Intent(context, AlarmReceiver::class.java).apply {
                putExtra(SHOW_ID, show.id)
                putExtra(SHOW_TITLE, show.title)
            }
            val pendingIntent = PendingIntent.getBroadcast(context, show.id, intent, PendingIntent.FLAG_UPDATE_CURRENT)

           val manager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val calendar = Calendar.getInstance()
            calendar.time = show.dueDate
            manager.set(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )

        }

    }
}