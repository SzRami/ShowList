package hu.bme.aut.android.showlist.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationManagerCompat

class MuteBroadcastReceiver: BroadcastReceiver()
{
    override fun onReceive(context: Context, intent: Intent)
    {
        with(NotificationManagerCompat.from(context.applicationContext)) {
            cancelAll()
        }
    }
}