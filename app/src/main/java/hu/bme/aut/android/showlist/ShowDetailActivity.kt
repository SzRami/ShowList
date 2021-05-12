package hu.bme.aut.android.showlist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import hu.bme.aut.android.showlist.databinding.ActivityShowDetailBinding
import hu.bme.aut.android.showlist.model.Show
import hu.bme.aut.android.showlist.viewmodel.ShowViewModel

class ShowDetailActivity: AppCompatActivity()
{
    private lateinit var binding: ActivityShowDetailBinding


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        binding = ActivityShowDetailBinding.inflate(layoutInflater)

        val showViewModel = ViewModelProvider(this).get(ShowViewModel::class.java)
        showViewModel.getShowById(intent.getIntExtra(ShowListActivity.SHOW_ID, 0)).observe(this,  {show ->
            binding.titleDetail.text = show?.title
            binding.typeDetail.text = show?.type
            binding.episodeDetail.text = show?.episode
            binding.descriptionDetail.text = show?.description
            binding.dueDateDetail.text = show?.dueDate.toString()
        })

        setContentView(binding.root)
    }
}