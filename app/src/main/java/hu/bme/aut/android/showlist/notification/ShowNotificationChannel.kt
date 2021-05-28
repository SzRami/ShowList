package hu.bme.aut.android.showlist.notification

enum class ShowNotificationChannel
(
    val id: String,
    val channelName: String,
    val channelDescription: String
){
    TO_WATCH( "hu.bme.aut.android.towatch", "To watch", "Notifications to watch a show")
}