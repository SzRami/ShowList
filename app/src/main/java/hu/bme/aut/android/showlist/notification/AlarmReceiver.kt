package hu.bme.aut.android.showlist.notification

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import hu.bme.aut.android.showlist.R
import hu.bme.aut.android.showlist.ShowDetailActivity
import hu.bme.aut.android.showlist.ShowListActivity
import hu.bme.aut.android.showlist.viewmodel.ShowViewModel
import kotlin.random.Random

class AlarmReceiver : BroadcastReceiver()
{
    override fun onReceive(context: Context?, intent: Intent?)
    {
        val id = intent?.getIntExtra(ShowNotificationHelper.SHOW_ID, 0) ?: 0
        val title = intent?.getStringExtra(ShowNotificationHelper.SHOW_TITLE) ?: ""
        val muteIntent = Intent(context, MuteBroadcastReceiver::class.java)
        val mutePendingIntent: PendingIntent =
            PendingIntent.getBroadcast(context, id, muteIntent, 0)
        val loadIntent = Intent(context!!, ShowListActivity::class.java)
        val pendingLoadIntent = PendingIntent.getActivity(context,0,loadIntent,0)
        val builder =
            NotificationCompat.Builder(context, ShowNotificationChannel.TO_WATCH.id)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("Go watch " + title)
                .setContentText("You must watch it now!!!!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingLoadIntent)
                .addAction(
                    0,
                    "Mute",
                    mutePendingIntent
                )
                .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            notify(Random.Default.nextInt(10000, 100000), builder.build())
        }
    }


}