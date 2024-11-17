package com.example.watchlistapp.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.watchlistapp.data.WatchItem
import com.example.watchlistapp.repository.WatchListRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class WatchListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = WatchListRepository(application)
    val watchList = mutableStateListOf<WatchItem>()

    init {
        loadWatchList()
    }

    private fun loadWatchList() {
        viewModelScope.launch {
            repository.watchListFlow.collectLatest { list ->
                if (list.isEmpty()) {
                    // Use mocked data if the list is empty
                    for (i in 1..100) {
                        watchList.add(WatchItem("Item $i"))
                    }
                } else {
                    // Load persisted data
                    watchList.clear()
                    watchList.addAll(list)
                }
            }
        }
    }

    fun addItem(title: String) {
        if (title.isNotBlank()) {
            val newItem = WatchItem(title)
            watchList.add(newItem)
            saveWatchList()
        }
    }

    fun removeItem(item: WatchItem) {
        watchList.remove(item)
        saveWatchList()
    }

    fun toggleWatched(item: WatchItem) {
        val index = watchList.indexOf(item)
        if (index != -1) {
            watchList[index] = watchList[index].copy(isWatched = !watchList[index].isWatched)
            saveWatchList()
        }
    }

    private fun saveWatchList() {
        viewModelScope.launch {
            repository.saveWatchList(watchList)
        }
    }
}
