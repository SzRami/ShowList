package hu.bme.aut.android.showlist.adapter

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.showlist.R
import hu.bme.aut.android.showlist.ShowListActivity
import hu.bme.aut.android.showlist.model.Show
import hu.bme.aut.android.showlist.notification.AlarmReceiver
import hu.bme.aut.android.showlist.notification.ShowNotificationHelper
import hu.bme.aut.android.showlist.viewmodel.ShowViewModel
import java.util.*

class ShowRecyclerViewAdapter(
    private var shows: List<Show>,
    private val showClickListener: ShowClickListener
) : RecyclerView.Adapter<ShowRecyclerViewAdapter.ViewHolder>()
{
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
    {
        val title: TextView = view.findViewById(R.id.title_text)
        val type: TextView = view.findViewById(R.id.type_text)
        val watched: CheckBox = view.findViewById(R.id.watched_checkbox)
    }

    interface ShowClickListener
    {
        fun onClick(show: Show)
        fun onLongClick(show: Show, view: View): Boolean
        fun watchedClick(show: Show)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.show, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        val show = shows[position]
        holder.itemView.setOnClickListener {
            showClickListener.onClick(show)
        }

        holder.itemView.setOnLongClickListener { view ->
            showClickListener.onLongClick(show, view)
            true
        }
        holder.title.text = show.title
        holder.type.text = show.type
        holder.watched.isChecked = show.isWatched

        holder.watched.setOnClickListener {
            holder.watched.isChecked = !holder.watched.isChecked
            showClickListener.watchedClick(show)
        }
        val intent = Intent(holder.itemView.context, AlarmReceiver::class.java).apply {
            putExtra(ShowNotificationHelper.SHOW_ID, show.id)
            putExtra(ShowNotificationHelper.SHOW_TITLE, show.title)
        }
        val pendingIntent = PendingIntent.getBroadcast(holder.itemView.context, show.id, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val manager = holder.itemView.context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        if (holder.watched.isChecked)
        {
            pendingIntent.cancel()
            manager.cancel(pendingIntent)
        }
    }

    override fun getItemCount() = shows.size

    fun setShows(shows: List<Show>)
    {
        this.shows = shows.toList()
        notifyDataSetChanged()
    }

}