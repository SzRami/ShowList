package hu.bme.aut.android.showlist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.android.showlist.adapter.ShowRecyclerViewAdapter
import hu.bme.aut.android.showlist.databinding.ActivityShowListBinding
import hu.bme.aut.android.showlist.model.Show
import hu.bme.aut.android.showlist.notification.ShowNotificationHelper
import hu.bme.aut.android.showlist.viewmodel.ShowViewModel

class ShowListActivity : AppCompatActivity(), ShowRecyclerViewAdapter.ShowClickListener,
    ShowCreateFragment.ShowCreatedListener
{
    companion object
    {
        val SHOW_ID = "SHOW_ID"
    }

    private lateinit var binding: ActivityShowListBinding
    private lateinit var showRecyclerViewAdapter: ShowRecyclerViewAdapter
    private lateinit var showViewModel: ShowViewModel

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityShowListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showViewModel = ViewModelProvider(this).get(ShowViewModel::class.java)

        showRecyclerViewAdapter = ShowRecyclerViewAdapter(emptyList(), this)
        showViewModel.allShows.observe(this, { shows ->
            showRecyclerViewAdapter.setShows(shows)
        })
        binding.showList.adapter = showRecyclerViewAdapter
        binding.showList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean
    {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        if (item.itemId == R.id.create_show)
        {
            val showCreateFragment = ShowCreateFragment()
            showCreateFragment.show(supportFragmentManager, "TAG")
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(show: Show)
    {
        val intent = Intent(this, ShowDetailActivity::class.java)
        intent.putExtra(SHOW_ID, show.id)
        startActivity(intent)
    }

    override fun onLongClick(show: Show, view: View): Boolean
    {
        val popup = PopupMenu(this, view)
        popup.inflate(R.menu.show_menu)
        popup.setOnMenuItemClickListener { item ->
            when (item.itemId)
            {
                R.id.delete ->
                {
                    showViewModel.delete(show)
                    return@setOnMenuItemClickListener true
                }
            }
            false
        }
        popup.show()
        return false
    }

    override fun watchedClick(show: Show)
    {
        showViewModel.update(Show(
            show.id,
            show.title,
            show.type,
            !show.isWatched,
            show.description,
            show.dueDate,
            show.episode
        ))
    }

    override fun onShowCreated(show: Show)
    {
        showViewModel.insert(show)
        ShowNotificationHelper.createToWatchNotification(this, show)
    }
}