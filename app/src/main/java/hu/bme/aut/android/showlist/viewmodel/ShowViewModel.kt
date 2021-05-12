package hu.bme.aut.android.showlist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.bme.aut.android.showlist.ShowApplication
import hu.bme.aut.android.showlist.model.Show
import hu.bme.aut.android.showlist.repository.ShowRepository
import kotlinx.coroutines.launch

class ShowViewModel: ViewModel()
{
    private val repository : ShowRepository
    val allShows : LiveData<List<Show>>

    init
    {
        val showDao = ShowApplication.showDatabase.showDao()
        repository = ShowRepository(showDao)
        allShows = repository.getAllShows()
    }

    fun insert(show: Show) = viewModelScope.launch {
        repository.insert(show)
    }

    fun delete(show: Show) = viewModelScope.launch {
        repository.delete(show)
    }
}