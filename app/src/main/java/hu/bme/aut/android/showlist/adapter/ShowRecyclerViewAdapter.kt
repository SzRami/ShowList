package hu.bme.aut.android.showlist.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.showlist.R
import hu.bme.aut.android.showlist.model.Show

class ShowRecyclerViewAdapter(
    private var shows: List<Show>,
    private val showClickListener: ShowClickListener
) : RecyclerView.Adapter<ShowRecyclerViewAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val watched: CheckBox = view.findViewById(R.id.watched_checkbox)
        val title: TextView = view.findViewById(R.id.title_text)
    }

    interface ShowClickListener {
        fun onClick(show: Show)

        fun onLongClick(show: Show, view: View): Boolean
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.show, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val show = shows[position]
        holder.itemView.setOnClickListener {
            showClickListener.onClick(show)
        }

        holder.itemView.setOnLongClickListener{ view ->
            showClickListener.onLongClick(show,view)
            true
        }
        holder.title.text = show.title
        holder.watched.isChecked = show.isWatched
    }

    override fun getItemCount() = shows.size

    fun setShows(shows: List<Show>) {
        this.shows = shows.toList()
        notifyDataSetChanged()
    }

}