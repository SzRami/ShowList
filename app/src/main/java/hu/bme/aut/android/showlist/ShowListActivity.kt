package hu.bme.aut.android.showlist

import android.icu.lang.UCharacter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.showlist.adapter.ShowRecyclerViewAdapter
import hu.bme.aut.android.showlist.databinding.ActivityShowListBinding
import hu.bme.aut.android.showlist.model.Show

class ShowListActivity : AppCompatActivity(), ShowRecyclerViewAdapter.ShowClickListener {
    private lateinit var binding: ActivityShowListBinding
    private lateinit var showRecyclerViewAdapter: ShowRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showRecyclerViewAdapter = ShowRecyclerViewAdapter(emptyList(), this)
        binding.showList.adapter = showRecyclerViewAdapter
        binding.showList.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.create_show) {

        } else if (item.itemId == R.id.delete_all) {

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(show: Show) {

    }

    override fun onLongClick(show: Show) {

    }
}